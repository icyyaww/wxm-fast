package com.wxm.msfast.base.auth.controller;

import com.wxm.msfast.base.auth.annotation.AuthIgnore;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.common.constant.ServiceNameConstants;
import com.wxm.msfast.base.common.web.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @program: msfast-parent
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 10:37
 **/
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @AuthIgnore
    @PostMapping("/login")
    public R<LoginUserResponse> login(@Valid @RequestBody LoginRequest request) {

        return R.ok(tokenService.login(request));
    }

    @AuthIgnore
    @DeleteMapping("/logout")
    public R<Void> logout() {
        tokenService.logout();
        return R.ok();
    }
}


