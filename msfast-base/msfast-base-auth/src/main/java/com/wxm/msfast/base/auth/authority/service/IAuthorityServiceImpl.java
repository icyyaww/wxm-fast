package com.wxm.msfast.base.auth.authority.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.common.rest.request.SmsLoginRequest;
import com.wxm.msfast.base.common.entity.LoginUser;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-23 15:04
 **/

public class IAuthorityServiceImpl<T extends LoginRequest, R extends RegisterRequest> implements IAuthorityService<T, R> {

    @Override
    public void register(R registerRequest) {

    }

    @Override
    public LoginUser login(T loginRequest) {
        LoginUser loginUser = new LoginUser();
        return loginUser;
    }

    @Override
    public LoginUser smsLogin(SmsLoginRequest loginRequest) {
        LoginUser loginUser = new LoginUser();
        return loginUser;
    }

    @Override
    public void logout() {

    }

    @Override
    public void sendSmsBefore(SendSmsRequest sendSmsRequest) {

    }

    @Override
    public void wxAppletRegister(R request) {

    }
}
