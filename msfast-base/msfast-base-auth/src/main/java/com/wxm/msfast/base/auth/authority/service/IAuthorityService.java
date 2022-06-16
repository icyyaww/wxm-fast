package com.wxm.msfast.base.auth.authority.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.entity.LoginUser;

public interface IAuthorityService {

    LoginUser login(LoginRequest loginRequest);
}
