package com.wxm.msfast.nostalgia.authority;

import com.wxm.msfast.base.auth.authority.service.AuthorityServiceImpl;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.file.service.MsfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 18:05
 **/
@Service
public class UserAuthorityServiceImpl extends AuthorityServiceImpl<LoginRequest, RegisterRequest> {


    @Autowired
    MsfFileService fileService;

}
