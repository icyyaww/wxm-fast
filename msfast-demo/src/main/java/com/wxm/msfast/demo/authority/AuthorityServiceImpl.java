package com.wxm.msfast.demo.authority;

import com.wxm.msfast.base.auth.authority.service.IAuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.entity.LoginUser;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 18:05
 **/
@Service
public class AuthorityServiceImpl implements IAuthorityService {
    @Override
    public LoginUser login(LoginRequest loginRequest) {
        LoginUser loginUser = new LoginUser();
        loginUser.setSuccess(true);
        loginUser.setUsername(loginRequest.getUsername());
        return loginUser;
    }
}
