package com.wxm.msfast.base.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.common.enums.LoginType;
import com.wxm.msfast.base.auth.common.rest.request.CheckSmsRequest;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.request.RegisterRequest;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.auth.utils.ReflexUtils;
import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.constant.ParamTypeConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.common.utils.ViolationUtils;
import com.wxm.msfast.base.common.web.domain.R;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestBody, name = "body", value = "{\n" +
                    "  \"username\":\"用户名 必填 loginType为WX_Applet时传code\",\n" +
                    "  \"password\":\"登录密码 可选 loginType为Number_Password时为必填\",\n" +
                    "  \"loginType\":\"登录方式 可选 Number_Password-账号密码登录,WX_Applet-微信小程序\"\n" +
                    "}", required = true)
    })
    public R<LoginUserResponse> login(@RequestBody @Valid @ApiIgnore String viewmodelJson) {

        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);

        Class<? extends LoginRequest> clsViewModel = ReflexUtils.getServiceViewModel(authorityService);

        LoginRequest viewModel = JSONObject.parseObject(viewmodelJson, clsViewModel);

        //数据校验
        ViolationUtils.violation(viewModel);

        if (viewModel.getLoginType() == null || LoginType.Number_Password.equals(viewModel.getLoginType())) {
            if (StringUtils.isBlank(viewModel.getPassword())) {
                //登陆失败
                throw new JrsfException(BaseExceptionEnum.PASSWORD_ISEMPTY);
            }
        }
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


