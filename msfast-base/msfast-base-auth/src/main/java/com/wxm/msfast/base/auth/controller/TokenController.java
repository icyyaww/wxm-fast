package com.wxm.msfast.base.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.CheckSmsRequest;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.auth.utils.ReflexUtils;
import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.common.utils.ViolationUtils;
import com.wxm.msfast.base.common.web.domain.R;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: msfast-parent
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 10:37
 **/
@RestController
@RequestMapping("/token")
@Api(tags = "用户信息")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @AuthIgnore
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    @ApiOperationSort(1)
    public R<Void> register(@RequestBody @Valid String viewmodelJson) {

        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);

        Class<? extends RegisterRequest> clsViewModel = ReflexUtils.getServiceView(authorityService);

        RegisterRequest viewModel = JSONObject.parseObject(viewmodelJson, clsViewModel);

        //数据校验
        ViolationUtils.violation(viewModel);
        tokenService.register(viewModel);
        return R.ok();
    }

    @AuthIgnore
    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    @ApiOperationSort(2)
    public R<LoginUserResponse> login(@RequestBody @Valid String viewmodelJson) {

        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);

        Class<? extends LoginRequest> clsViewModel = ReflexUtils.getServiceViewModel(authorityService);

        LoginRequest viewModel = JSONObject.parseObject(viewmodelJson, clsViewModel);

        //数据校验
        ViolationUtils.violation(viewModel);
        return R.ok(tokenService.login(viewModel));
    }

    @AuthIgnore
    @DeleteMapping("/logout")
    @ApiOperation(value = "退出登陆")
    @ApiOperationSort(3)
    public R<Void> logout() {
        tokenService.logout();
        return R.ok();
    }

    @AuthIgnore
    @ApiOperation(value = "发送短信验证码")
    @PostMapping("/sendsms")
    @ApiOperationSort(4)
    public R<Void> sendSms(@RequestBody @Valid SendSmsRequest sendSmsRequest) {
        tokenService.sendSms(sendSmsRequest);
        return R.ok();
    }

    @AuthIgnore
    @ApiOperation(value = "校验短信验证码")
    @PostMapping("/checksms")
    @ApiOperationSort(5)
    public R<Void> checkSms(@RequestBody @Valid CheckSmsRequest checkSmsRequest) {
        tokenService.checkSms(checkSmsRequest);
        return R.ok();
    }
}


