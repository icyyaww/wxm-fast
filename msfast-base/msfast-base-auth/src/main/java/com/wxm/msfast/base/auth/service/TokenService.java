package com.wxm.msfast.base.auth.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;

public interface TokenService {

    LoginUserResponse login(LoginRequest request);
}
