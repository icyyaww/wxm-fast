package com.wxm.msfast.community.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.page.Query;

import com.wxm.msfast.community.dao.FrBlogDao;
import com.wxm.msfast.community.entity.FrBlogEntity;
import com.wxm.msfast.community.service.FrBlogService;


@Service("frBlogService")
public class FrBlogServiceImpl extends ServiceImpl<FrBlogDao, FrBlogEntity> implements FrBlogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FrBlogEntity> page = this.page(
                new Query<FrBlogEntity>().getPage(params),
                new QueryWrapper<FrBlogEntity>()
        );

        return new PageUtils(page);
    }

}
