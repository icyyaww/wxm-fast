package com.wxm.msfast.base.auth.authority.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.common.rest.request.SmsLoginRequest;
import com.wxm.msfast.base.common.entity.LoginUser;

/*
 * @Author
 * @Description  有关权限相关的业务代码实现
 * @Date 21:45 2022/6/18
 **/
public interface IAuthorityService<T extends LoginRequest, R extends RegisterRequest> {

    void register(R registerRequest);

    LoginUser login(T loginRequest);

    LoginUser smsLogin(SmsLoginRequest loginRequest);

    void logout();

    void sendSmsBefore(SendSmsRequest sendSmsRequest);

    void wxAppletRegister(R request);
}
