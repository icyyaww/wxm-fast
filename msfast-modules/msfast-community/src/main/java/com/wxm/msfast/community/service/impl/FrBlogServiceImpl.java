package com.wxm.msfast.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.community.entity.FrUserEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.community.dao.FrBlogDao;
import com.wxm.msfast.community.entity.FrBlogEntity;
import com.wxm.msfast.community.service.FrBlogService;


@Service("frBlogService")
public class FrBlogServiceImpl extends ServiceImpl<FrBlogDao, FrBlogEntity> implements FrBlogService {


    @Override
    public Long userBlogCount() {


        Wrapper<FrBlogEntity> frBlogEntityWrapper = new QueryWrapper<FrBlogEntity>().lambda()
                .eq(FrBlogEntity::getUserId, TokenUtils.getOwnerId());
        Long blogCount = this.baseMapper.selectCount(frBlogEntityWrapper);
        return blogCount;
    }
}
