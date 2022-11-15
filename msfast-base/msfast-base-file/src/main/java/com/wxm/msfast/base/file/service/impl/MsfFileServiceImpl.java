package com.wxm.msfast.base.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.file.annotation.FileSave;
import com.wxm.msfast.base.file.common.enums.FileStatusEnum;
import com.wxm.msfast.base.file.config.MinioConfig;
import com.wxm.msfast.base.file.dao.MsfFileDao;
import com.wxm.msfast.base.file.entity.MsfFileEntity;
import com.wxm.msfast.base.file.exception.TempFileNoExistException;
import com.wxm.msfast.base.file.service.MsfFileService;
import com.wxm.msfast.base.file.utils.DelayTaskProducer;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Calendar;

@Service("msfFileService")
public class MsfFileServiceImpl extends ServiceImpl<MsfFileDao, MsfFileEntity> implements MsfFileService {

    @Autowired
    private MinioClient client;

    @Autowired
    private MinioConfig minioConfig;


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

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ConfigConstants.FILE_TEMP_TIME());//30分钟后文件删除
        DelayTaskProducer.put(filePath, url, calendar.getTimeInMillis());
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
                        if (urlObject != null) {
                            changeTempUrl(urlObject.toString());
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
        try {
            Wrapper<MsfFileEntity> wrapper = new QueryWrapper<MsfFileEntity>().lambda()
                    .eq(MsfFileEntity::getUrl, url)
                    .eq(MsfFileEntity::getStatus, FileStatusEnum.TEMP);
            Long tempCount = this.getBaseMapper().selectCount(wrapper);
            if (tempCount > 0) {
                deleteFile(filePath);
                this.remove(wrapper);
            }
        } catch (Exception e) {
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
    public void deleteFile(String filePath) throws Exception {
        client.removeObject(
                RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filePath).build());
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
        } else {
            DelayTaskProducer.remove(url);
        }
    }
}
