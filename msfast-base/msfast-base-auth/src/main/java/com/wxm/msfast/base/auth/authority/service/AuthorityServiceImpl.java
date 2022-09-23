package com.wxm.msfast.base.auth.authority.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.common.web.domain.R;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-23 15:04
 **/

public class AuthorityServiceImpl<T extends LoginRequest, R extends RegisterRequest> implements AuthorityService<T, R> {

    @Override
    public void register(R registerRequest) {

    }

    @Override
    public LoginUser login(T loginRequest) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1l);
        loginUser.setUsername(loginRequest.getUsername());
        return loginUser;
    }

    @Override
    public void logout() {

    }
}
