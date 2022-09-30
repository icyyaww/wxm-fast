package com.wxm.msfast.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.wxm.msfast.base.auth.common.enums.MessageType;
import com.wxm.msfast.base.auth.common.rest.request.CheckSmsRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.community.common.exception.UserExceptionEnum;
import com.wxm.msfast.community.common.rest.request.user.SmsLoginRequest;
import com.wxm.msfast.community.common.rest.request.user.UserLoginRequest;
import com.wxm.msfast.community.common.rest.response.user.DynamicUserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    TokenService tokenService;

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

    @Override
    public FrUserEntity getFrUserByPhone(String phone) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getPhone, phone);
        return getOne(frUserEntityWrapper);
    }

    @Override
    public LoginUserResponse smsLogin(SmsLoginRequest request) {

        FrUserEntity frUserEntity = getFrUserByPhone(request.getPhone());
        if (frUserEntity == null) {
            throw new JrsfException(UserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }
        CheckSmsRequest checkSmsRequest = new CheckSmsRequest();
        checkSmsRequest.setMessageType(MessageType.LOGIN);
        BeanUtils.copyProperties(request, checkSmsRequest);
        tokenService.checkSms(checkSmsRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();


        userLoginRequest.setUsername(request.getPhone());
        userLoginRequest.setPassword(frUserEntity.getPassword());

        return tokenService.login(userLoginRequest);
    }

    @Override
    public List<DynamicUserResponse> getDynamicUser() {
        FrUserEntity frUserEntity = getById(TokenUtils.getUserId());
        return this.baseMapper.getDynamicUser(frUserEntity.getGender(), frUserEntity.getId());
    }

}
