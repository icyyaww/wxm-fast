package com.wxm.msfast.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.nostalgia.common.exception.UserExceptionEnum;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.LoginResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendUserInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.FrUserDao;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Override
    public Long countByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return count(frUserEntityWrapper);
    }

    @Override
    public FrUserEntity getFrUserByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return getOne(frUserEntityWrapper);
    }

    @Override
    public RecommendUserInfoResponse recommendUserInfo(RecommendUserRequest request) {

        LoginUser<LoginResponse> loginUser = TokenUtils.info(LoginResponse.class);
        if (loginUser == null) {
            //未登陆
            if (request == null || request.getAge() == null || request.getGender() == null) {
                throw new JrsfException(UserExceptionEnum.SEARCH_PARAM_EMPTY_EXCEPTION);
            }
            FrUserEntity frUserEntity = this.getById(1);
            RecommendUserInfoResponse recommendUserInfo = new RecommendUserInfoResponse();
            BeanUtils.copyProperties(frUserEntity, recommendUserInfo);
            return recommendUserInfo;
        } else {
            //已登录
        }
        return null;
    }
}
