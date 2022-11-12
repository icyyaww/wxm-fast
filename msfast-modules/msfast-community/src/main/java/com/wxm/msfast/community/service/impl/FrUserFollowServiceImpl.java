package com.wxm.msfast.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.community.dao.FrUserFollowDao;
import com.wxm.msfast.community.entity.FrUserFollowEntity;
import com.wxm.msfast.community.service.FrUserFollowService;
import org.springframework.stereotype.Service;


@Service("frUserFollowService")
public class FrUserFollowServiceImpl extends ServiceImpl<FrUserFollowDao, FrUserFollowEntity> implements FrUserFollowService {

    @Override
    public Long followUserCount() {
        Wrapper<FrUserFollowEntity> frUserFollowEntityWrapper = new QueryWrapper<FrUserFollowEntity>()
                .lambda().eq(FrUserFollowEntity::getUserId, TokenUtils.getOwnerId());
        Long followCount = this.baseMapper.selectCount(frUserFollowEntityWrapper);
        return followCount;
    }

    @Override
    public Long fansCount() {
        Wrapper<FrUserFollowEntity> frUserFollowEntityWrapper = new QueryWrapper<FrUserFollowEntity>()
                .lambda().eq(FrUserFollowEntity::getFollowId, TokenUtils.getOwnerId());
        Long fansCount = this.baseMapper.selectCount(frUserFollowEntityWrapper);
        return fansCount;
    }
}
