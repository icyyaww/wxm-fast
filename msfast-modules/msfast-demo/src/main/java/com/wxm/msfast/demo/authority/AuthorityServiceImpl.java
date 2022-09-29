package com.wxm.msfast.demo.authority;

import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.demo.common.rest.request.user.UserLoginRequest;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 18:05
 **/
@Service
public class AuthorityServiceImpl implements AuthorityService<UserLoginRequest, RegisterRequest> {

    @Override
    public void register(RegisterRequest registerRequest) {

    }

    @Override
    public LoginUser login(UserLoginRequest loginRequest) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        return loginUser;
    }

    @Override
    public void logout() {

    }

    @Override
    public void sendSmsBefore(SendSmsRequest sendSmsRequest) {

    }

}
