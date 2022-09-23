package com.wxm.msfast.community.authority;

import com.wxm.msfast.base.auth.authority.service.AuthorityServiceImpl;
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
public class UserAuthorityServiceImpl extends AuthorityServiceImpl<UserLoginRequest, UserRegisterRequest> {
    @Override
    public void register(UserRegisterRequest registerRequest) {
        super.register(registerRequest);
        System.out.println(registerRequest);
    }
}
