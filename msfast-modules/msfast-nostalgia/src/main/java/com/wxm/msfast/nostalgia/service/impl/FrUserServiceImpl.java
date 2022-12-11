package com.wxm.msfast.nostalgia.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.auth.service.MsfConfigService;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.nostalgia.common.enums.SysConfigCodeEnum;
import com.wxm.msfast.nostalgia.common.exception.UserExceptionEnum;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.LoginResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendUserInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.FrUserDao;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Autowired
    MsfConfigService msfConfigService;

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
    public RecommendUserInfoResponse getRecommendUserInfo(RecommendUserRequest request) {

        LoginUser<LoginResponse> loginUser = TokenUtils.info(LoginResponse.class);
        if (loginUser == null) {
            //未登陆
            if (request == null || request.getAge() == null || request.getGender() == null) {
                throw new JrsfException(UserExceptionEnum.SEARCH_PARAM_EMPTY_EXCEPTION);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("gender", request.getGender().name());
            //todo 增加年龄筛选

            RecommendUserInfoResponse userInfoResponse = getRecommendUserInfoByParam(param);
            if (userInfoResponse != null) {
                userInfoResponse.setSurplusNum(Integer.valueOf(msfConfigService.getValueByCode(SysConfigCodeEnum.recommendTotal.name())));
            }
            return userInfoResponse;

        } else {
            //已登录
            Map<String, Object> param = new HashMap<>();
            param.put("gender", loginUser.getInfo().getGender().name());
            param.put("selfId", loginUser.getId());
            return getRecommendUserInfoByParam(param);
        }
    }

    private RecommendUserInfoResponse getRecommendUserInfoByParam(Map<String, Object> param) {

        List<RecommendUserInfoResponse> list = this.baseMapper.getRecommendUserInfo(param);
        if (CollectionUtil.isNotEmpty(list)) {
            RecommendUserInfoResponse recommendUserInfo = new RecommendUserInfoResponse();
            BeanUtils.copyProperties(list.get(0), recommendUserInfo);
            return recommendUserInfo;
        }
        return null;
    }
}
