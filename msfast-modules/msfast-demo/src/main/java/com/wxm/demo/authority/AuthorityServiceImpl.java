package com.wxm.demo.authority;

import com.wxm.base.auth.authority.service.IAuthorityServiceImpl;
import com.wxm.base.auth.common.rest.request.LoginRequest;
import com.wxm.base.auth.common.rest.request.RegisterRequest;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 18:05
 **/
@Service
public class AuthorityServiceImpl extends IAuthorityServiceImpl<LoginRequest, RegisterRequest> {

}
