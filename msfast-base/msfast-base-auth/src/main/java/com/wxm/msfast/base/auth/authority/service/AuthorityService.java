package com.wxm.msfast.base.auth.authority.service;

import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.entity.LoginUser;

/*
 * @Author
 * @Description  有关权限相关的业务代码实现
 * @Date 21:45 2022/6/18
 **/
public interface AuthorityService {

    LoginUser login(LoginRequest loginRequest);

    Boolean hasPermission();

    void logout();
}
