package com.wxm.msfast.community.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.page.Query;

import com.wxm.msfast.community.dao.FrUserFollowDao;
import com.wxm.msfast.community.entity.FrUserFollowEntity;
import com.wxm.msfast.community.service.FrUserFollowService;


@Service("frUserFollowService")
public class FrUserFollowServiceImpl extends ServiceImpl<FrUserFollowDao, FrUserFollowEntity> implements FrUserFollowService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FrUserFollowEntity> page = this.page(
                new Query<FrUserFollowEntity>().getPage(params),
                new QueryWrapper<FrUserFollowEntity>()
        );

        return new PageUtils(page);
    }

}
