package com.wxm.msfast.base.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.file.common.enums.FileStatusEnum;
import com.wxm.msfast.base.file.dao.MsfFileDao;
import com.wxm.msfast.base.file.entity.MsfFileEntity;
import com.wxm.msfast.base.file.service.MsfFileService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("msfFileService")
public class MsfFileServiceImpl extends ServiceImpl<MsfFileDao, MsfFileEntity> implements MsfFileService {

    @Override
    @Transactional
    @Async
    public void saveFile(String url, String fileName) {
        MsfFileEntity msfFileEntity = new MsfFileEntity();
        msfFileEntity.setUrl(url);
        msfFileEntity.setOriginal(true);
        msfFileEntity.setName(fileName);
        msfFileEntity.setStatus(FileStatusEnum.TEMP);
        this.save(msfFileEntity);
    }
}
