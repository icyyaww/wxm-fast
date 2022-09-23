package com.wxm.msfast.community.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.page.Query;

import com.wxm.msfast.community.dao.FrUserDao;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FrUserEntity> page = this.page(
                new Query<FrUserEntity>().getPage(params),
                new QueryWrapper<FrUserEntity>()
        );

        return new PageUtils(page);
    }

}
