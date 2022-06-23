package com.wxm.msfast.base.auth.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;

public interface TokenService {

    LoginUserResponse login(LoginRequest request);

    void logout();

    void refreshToken(String token);
}
