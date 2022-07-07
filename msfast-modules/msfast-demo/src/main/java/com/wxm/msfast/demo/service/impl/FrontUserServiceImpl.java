package com.wxm.msfast.demo.service.impl;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.page.Query;

import com.wxm.msfast.demo.dao.FrontUserDao;
import com.wxm.msfast.demo.entity.FrontUserEntity;
import com.wxm.msfast.demo.service.FrontUserService;
import org.springframework.transaction.annotation.Transactional;


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
    public void versionLock(Long id) {
        FrontUserEntity frontUserEntity = this.baseMapper.selectById(id);
        int result = this.baseMapper.updateById(frontUserEntity);
        System.out.println(result);
    }

}
