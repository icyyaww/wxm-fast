package com.wxm.msfast.nostalgia.authority;

import com.wxm.msfast.base.auth.authority.service.IAuthorityServiceImpl;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.entity.LoginUser;
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
public class AuthorityServiceImpl extends IAuthorityServiceImpl<LoginRequest, RegisterRequest> {

    @Autowired
    MsfFileService fileService;

    /*
     * @Author wanglei
     * @Description  微信小程序登录
     * @Date 16:49 2022/12/5
     * @Param
     * @return
     **/
    @Override
    public LoginUser login(LoginRequest loginRequest) {
        LoginUser loginUser = new LoginUser();
        return loginUser;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        super.register(registerRequest);
    }
}
