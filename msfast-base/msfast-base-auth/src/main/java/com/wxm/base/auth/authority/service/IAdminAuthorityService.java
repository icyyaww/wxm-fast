package com.wxm.base.auth.authority.service;

import com.wxm.base.auth.common.rest.request.LoginRequest;
import com.wxm.base.common.entity.LoginUser;

public interface IAdminAuthorityService<T extends LoginRequest> {

    LoginUser adminLogin(T loginRequest);

    void logout();
}
