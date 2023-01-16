package com.wxm.msfast.community.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.base.common.utils.TokenUtils;
import com.wxm.msfast.community.common.rest.response.user.PersonalCenterBlogResponse;
import com.wxm.msfast.community.dao.FrBlogDao;
import com.wxm.msfast.community.entity.FrBlogEntity;
import com.wxm.msfast.community.service.FrBlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("frBlogService")
public class FrBlogServiceImpl extends ServiceImpl<FrBlogDao, FrBlogEntity> implements FrBlogService {


    @Override
    public Long userBlogCount() {


        Wrapper<FrBlogEntity> frBlogEntityWrapper = new QueryWrapper<FrBlogEntity>().lambda()
                .eq(FrBlogEntity::getUserId, TokenUtils.getOwnerId());
        Long blogCount = this.baseMapper.selectCount(frBlogEntityWrapper);
        return blogCount;
    }

    @Override
    public List<PersonalCenterBlogResponse> getPersonalBlogImage() {

        Wrapper<FrBlogEntity> frBlogEntityWrapper = new QueryWrapper<FrBlogEntity>().lambda()
                .eq(FrBlogEntity::getUserId, TokenUtils.getOwnerId())
                .isNotNull(FrBlogEntity::getImages)
                .orderByDesc(FrBlogEntity::getCreateTime)
                .last("limit 2");
        List<FrBlogEntity> blogEntityList = this.baseMapper.selectList(frBlogEntityWrapper);
        List<PersonalCenterBlogResponse> blogResponseList = new ArrayList<>();
        blogEntityList.forEach(model -> {
            PersonalCenterBlogResponse personalCenterBlogResponse = new PersonalCenterBlogResponse();
            BeanUtils.copyProperties(model, personalCenterBlogResponse);
            if (CollectionUtil.isNotEmpty(model.getImages())) {
                personalCenterBlogResponse.setImage(model.getImages().get(0));
            }
            personalCenterBlogResponse.setTime(DateUtils.getChineseTime(model.getCreateTime()));
            blogResponseList.add(personalCenterBlogResponse);
        });
        return blogResponseList;
    }
}
