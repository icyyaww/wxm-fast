package com.wxm.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.base.common.exception.OptimisticLockerException;
import com.wxm.demo.entity.FrontUserEntity;
import com.wxm.demo.dao.FrontUserDao;
import com.wxm.demo.service.FrontUserService;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("frontUserService")
public class FrontUserServiceImpl extends ServiceImpl<FrontUserDao, FrontUserEntity> implements FrontUserService {


    @Override
    @Transactional
    @Retryable(value = OptimisticLockerException.class)
    public void versionLock(Long id) {
        FrontUserEntity frontUserEntity = this.baseMapper.selectById(id);
        int result = this.baseMapper.updateById(frontUserEntity);
        if (result == 0) {
            throw new OptimisticLockerException();
        }
        System.out.println(result);
    }


}
