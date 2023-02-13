package com.wxm.msfast.base.auth.authority.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.common.entity.LoginUser;

public interface IAdminAuthorityService<T extends LoginRequest> {

    LoginUser adminLogin(T loginRequest);

    void logout();
}
