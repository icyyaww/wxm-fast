package com.wxm.msfast.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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

    /**
     * @Description: 根据手机号查询数量
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/29 下午2:53
     */
    @Override
    public Long countByPhone(String phone) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getPhone, phone);
        return count(frUserEntityWrapper);
    }

}
