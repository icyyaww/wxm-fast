package com.wxm.base.auth.authority.service;

import com.wxm.base.auth.common.rest.request.LoginRequest;
import com.wxm.base.common.entity.LoginUser;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-13 10:41
 **/

public class IAdminAuthorityServiceImpl<T extends LoginRequest> implements IAdminAuthorityService<T> {
    @Override
    public LoginUser adminLogin(T loginRequest) {
        return null;
    }

    @Override
    public void logout() {

    }
}
