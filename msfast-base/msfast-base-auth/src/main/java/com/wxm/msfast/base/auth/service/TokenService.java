package com.wxm.msfast.base.auth.service;

import com.wxm.msfast.base.auth.common.rest.request.CheckSmsRequest;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;

public interface TokenService<T extends LoginRequest, R extends RegisterRequest> {

    void register(R request);

    LoginUserResponse login(T request);

    void logout();

    void refreshToken(String token);

    void sendSms(SendSmsRequest sendSmsRequest);

    void checkSms(CheckSmsRequest checkSmsRequest);

    void wxAppletRegister(R request);

    LoginUserResponse wxAppletLogin(T request);

    LoginUserResponse adminLogin(T request);
}
