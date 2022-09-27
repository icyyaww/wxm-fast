package com.wxm.msfast.base.auth.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface TokenService<T extends LoginRequest, R extends RegisterRequest> {

    void register(R request);

    LoginUserResponse login(T request);

    void logout();

    void refreshToken(String token);

    void sendSms(SendSmsRequest sendSmsRequest);

}
