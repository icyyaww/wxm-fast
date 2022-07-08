package com.wxm.msfast.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.exception.OptimisticLockerException;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.page.Query;
import com.wxm.msfast.demo.dao.FrontUserDao;
import com.wxm.msfast.demo.entity.FrontUserEntity;
import com.wxm.msfast.demo.service.FrontUserService;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service("frontUserService")
public class FrontUserServiceImpl extends ServiceImpl<FrontUserDao, FrontUserEntity> implements FrontUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FrontUserEntity> page = this.page(
                new Query<FrontUserEntity>().getPage(params),
                new QueryWrapper<FrontUserEntity>()
        );

        return new PageUtils(page);
    }


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
