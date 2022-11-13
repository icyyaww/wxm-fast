package com.wxm.msfast.base.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.file.annotation.FileSave;
import com.wxm.msfast.base.file.common.enums.FileStatusEnum;
import com.wxm.msfast.base.file.dao.MsfFileDao;
import com.wxm.msfast.base.file.entity.MsfFileEntity;
import com.wxm.msfast.base.file.exception.TempFileNoExistException;
import com.wxm.msfast.base.file.service.MsfFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@Service("msfFileService")
public class MsfFileServiceImpl extends ServiceImpl<MsfFileDao, MsfFileEntity> implements MsfFileService {

    @Override
    @Transactional
    @Async
    public void saveFile(String url, String fileName) {
        MsfFileEntity msfFileEntity = new MsfFileEntity();
        msfFileEntity.setUrl(url);
        msfFileEntity.setOriginal(true);
        msfFileEntity.setFileName(fileName);
        msfFileEntity.setStatus(FileStatusEnum.TEMP);
        this.save(msfFileEntity);
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
