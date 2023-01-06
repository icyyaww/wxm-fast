package com.wxm.msfast.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.RecommendConfigDao;
import com.wxm.msfast.nostalgia.entity.RecommendConfigEntity;
import com.wxm.msfast.nostalgia.service.RecommendConfigService;


@Service("recommendConfigService")
public class RecommendConfigServiceImpl extends ServiceImpl<RecommendConfigDao, RecommendConfigEntity> implements RecommendConfigService {

    @Override
    public RecommendConfigEntity getRecommendConfigByUserId(Integer userId) {
        Wrapper<RecommendConfigEntity> wrapper = new QueryWrapper<RecommendConfigEntity>().lambda().eq(RecommendConfigEntity::getUserId, userId);
        return this.baseMapper.selectOne(wrapper);
    }

}
