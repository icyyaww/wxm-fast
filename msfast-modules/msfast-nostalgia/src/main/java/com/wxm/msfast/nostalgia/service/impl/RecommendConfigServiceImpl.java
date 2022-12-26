package com.wxm.msfast.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.nostalgia.common.constant.Constants;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendConfigResponse;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.RecommendConfigDao;
import com.wxm.msfast.nostalgia.entity.RecommendConfigEntity;
import com.wxm.msfast.nostalgia.service.RecommendConfigService;

import java.util.ArrayList;
import java.util.List;


@Service("recommendConfigService")
public class RecommendConfigServiceImpl extends ServiceImpl<RecommendConfigDao, RecommendConfigEntity> implements RecommendConfigService {

    @Override
    public RecommendConfigEntity getRecommendConfigByUserId(Integer userId) {
        Wrapper<RecommendConfigEntity> wrapper = new QueryWrapper<RecommendConfigEntity>().lambda().eq(RecommendConfigEntity::getUserId, userId);
        return this.baseMapper.selectOne(wrapper);
    }

}
