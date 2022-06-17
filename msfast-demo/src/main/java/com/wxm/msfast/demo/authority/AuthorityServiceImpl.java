package com.wxm.msfast.demo.authority;

import com.wxm.msfast.base.auth.authority.service.AuthorityService;
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
public class AuthorityServiceImpl implements AuthorityService {

    /**
     * @Description: 用户登陆业务业务
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/17 上午10:12
     */
    @Override
    public LoginUser login(LoginRequest loginRequest) {
        LoginUser loginUser = new LoginUser();
        loginUser.setSuccess(true);
        loginUser.setUsername(loginRequest.getUsername());
        return loginUser;
    }
}
