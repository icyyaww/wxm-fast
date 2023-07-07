package com.wxm.base.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.base.common.constant.ConfigConstants;
import com.wxm.base.common.utils.ThreadUtil;
import com.wxm.base.file.service.MsfFileService;
import com.wxm.base.file.annotation.FileListSave;
import com.wxm.base.file.annotation.FileSave;
import com.wxm.base.file.common.enums.FileStatusEnum;
import com.wxm.base.file.config.MinioConfig;
import com.wxm.base.file.dao.MsfFileDao;
import com.wxm.base.file.entity.MsfFileEntity;
import com.wxm.base.file.exception.TempFileNoExistException;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("msfFileService")
public class MsfFileServiceImpl extends ServiceImpl<MsfFileDao, MsfFileEntity> implements MsfFileService {

    @Autowired
    private MinioClient client;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    @Async
    public void saveFile(String url, String filePath, String fileName) {
        MsfFileEntity msfFileEntity = new MsfFileEntity();
        msfFileEntity.setUrl(url);
        msfFileEntity.setOriginal(true);
        msfFileEntity.setFileName(fileName);
        msfFileEntity.setStatus(FileStatusEnum.TEMP);
        this.save(msfFileEntity);
        delayDeleteFile(filePath, url);
    }

    @Async
    void delayDeleteFile(String filePath, String url) {
        ThreadUtil.getInstance().scheduledThreadPool.schedule(() -> {
            Wrapper<MsfFileEntity> wrapper = new QueryWrapper<MsfFileEntity>().lambda()
                    .eq(MsfFileEntity::getUrl, url)
                    .eq(MsfFileEntity::getStatus, FileStatusEnum.TEMP);
            Long count = count(wrapper);
            if (count > 0) {
                deleteTempFile(filePath, url);
            }
        }, ConfigConstants.FILE_TEMP_TIME(), TimeUnit.MINUTES);
    }

    @Override
    @Async
    @Transactional
    @Retryable(value = TempFileNoExistException.class)
    public void changeTempFile(Object object) {
        if (object != null) {
            Field[] fieldMap = object.getClass().getDeclaredFields();
            for (Field field : fieldMap) {
                field.setAccessible(true);
                //有该注解表示这个字段是文件 需要将临时文件设置为持久化避免被删除
                FileSave fileSave = field.getAnnotation(FileSave.class);
                if (fileSave != null) {
                    try {
                        Object urlObject = field.get(object);
                        if (urlObject != null && urlObject instanceof String) {
                            getFileListByText(urlObject.toString()).forEach(model -> {
                                changeTempUrl(model);
                            });
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                FileListSave fileListSave = field.getAnnotation(FileListSave.class);
                if (fileListSave != null) {
                    try {
                        Object urlObject = field.get(object);
                        if (urlObject != null && urlObject instanceof List) {
                            ((List) urlObject).forEach(model -> {
                                if (model instanceof String) {
                                    changeTempUrl(model.toString());
                                }
                            });
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    @Async
    @Transactional
    @Retryable(value = TempFileNoExistException.class)
    public void changeTempFile(String url) {
        changeTempUrl(url);
    }

    @Override
    @Async
    @Transactional
    public void deleteTempFile(String filePath, String url) {

        Wrapper<MsfFileEntity> wrapper = new QueryWrapper<MsfFileEntity>().lambda()
                .eq(MsfFileEntity::getUrl, url)
                .eq(MsfFileEntity::getStatus, FileStatusEnum.TEMP);
        Long tempCount = this.getBaseMapper().selectCount(wrapper);
        if (tempCount > 0) {
            deleteFileByPath(filePath);
            this.remove(wrapper);
        }
    }

    /**
     * @param filePath
     * @Description: 删除文件 物理删除
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 下午3:39
     */
    @Override
    public void deleteFileByPath(String filePath) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filePath).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteFileByUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            String pre = getPrePath();
            int index = url.indexOf(pre);
            if (index >= 0) {
                String path = url.substring(pre.length());
                deleteFileByPath(path);
                Wrapper<MsfFileEntity> wrapper = new QueryWrapper<MsfFileEntity>().lambda()
                        .eq(MsfFileEntity::getUrl, url);
                this.remove(wrapper);
            }

        }
    }

    @Override
    public String getPrePath() {

        return (StringUtils.isNotBlank(minioConfig.getUrl()) ? minioConfig.getUrl() : minioConfig.getEndpoint()) + "/" + minioConfig.getBucketName() + "/";
    }

    @Override
    public void deleteImg(List<String> oldImg, List<String> imgList) {
        //todo 删除图片
        if (CollectionUtil.isNotEmpty(oldImg)) {

            oldImg.forEach(model -> {
                Long count = imgList.stream().filter(p -> StringUtils.isNotBlank(p) && p.equals(model)).count();
                if (count == 0) {
                    deleteFileByUrl(model);
                }
            });
        }
    }

    @Override
    public void deleteSaveFile(Object object, Integer ownerId) {

        Field[] Fields = ClassUtil.getDeclaredFields(object.getClass());
        for (Field field : Fields) {

            //图片地址
            FileSave fileSave = field.getAnnotation(FileSave.class);
            if (fileSave != null && StringUtils.isNotBlank(fileSave.table()) && StringUtils.isNotBlank(fileSave.field())) {
                Field fieldId = ClassUtil.getDeclaredField(object.getClass(), "id");

                if (fieldId != null || fileSave.tokenId()) {

                    Integer fieldIdValue = null;
                    if (fileSave.tokenId()) {
                        fieldIdValue = ownerId;
                    } else {
                        try {
                            fieldId.setAccessible(true);
                            Object objectId = fieldId.get(object);
                            if (objectId != null) {
                                fieldIdValue = (Integer) objectId;
                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    String urlSql = "select tb." + fileSave.field() + " from " + fileSave.table() + " tb where  tb.id=:id";
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("id", fieldIdValue);
                    List<String> urlList = namedParameterJdbcTemplate.queryForList(urlSql, param, String.class);
                    field.setAccessible(true);
                    try {
                        String newUrl = (String) field.get(object);
                        if (CollectionUtil.isNotEmpty(urlList)) {
                            List<String> oldFileList = getFileListByText(urlList.get(0));
                            List<String> newFileList = getFileListByText(newUrl);
                            deleteImg(oldFileList, newFileList);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public String getFileNameByUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            return url.substring(url.indexOf(minioConfig.getBucketName()) + minioConfig.getBucketName().length() + 1);
        }
        return null;
    }

    @Override
    public void deleteFileByRichText(String richText) {

        getFileListByText(richText).forEach(model -> {
            deleteFileByUrl(model);
        });
    }

    public List<String> getFileListByText(String richText) {

        List<String> imgList = new ArrayList<>();

        //提取图片地址
        String regex = "(((https|http)?:)?//?[^'\"<>]+?\\.(jpg|jpeg|gif|png|JPG|JPEG|GIF|PNG))";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(richText);
        while (m.find()) {
            //获取数字子串
            String num = m.group(1);
            if (num.contains(minioConfig.getBucketName())) {
                imgList.add(num);
            }
        }

        return imgList;
    }

    private void changeTempUrl(String url) {
        if (StringUtils.isBlank(url))
            return;
        Wrapper<MsfFileEntity> wrapper = new UpdateWrapper<MsfFileEntity>().lambda()
                .eq(MsfFileEntity::getUrl, url)
                .set(MsfFileEntity::getStatus, FileStatusEnum.SAVED);
        Boolean result = this.update(wrapper);
        if (result == false) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new TempFileNoExistException();
        }
    }

}
