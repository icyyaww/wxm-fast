package com.wxm.msfast.community.authority;

import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.community.common.rest.request.user.UserLoginRequest;
import com.wxm.msfast.community.common.rest.request.user.UserRegisterRequest;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 18:05
 **/
@Service
public class AuthorityServiceImpl implements AuthorityService<UserLoginRequest, UserRegisterRequest> {

    @Override
    public void register(UserRegisterRequest registerRequest) {
        System.out.println(registerRequest);
    }

    @Override

    public LoginUser login(UserLoginRequest loginRequest) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1l);
        loginUser.setUsername(loginRequest.getUsername());
        return loginUser;
    }

    @Override
    public void logout() {

    }
}
