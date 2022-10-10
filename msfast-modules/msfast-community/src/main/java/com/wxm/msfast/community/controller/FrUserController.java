package com.wxm.msfast.community.controller;

import com.wxm.msfast.base.auth.annotation.AuthIgnore;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.community.common.rest.request.user.SmsLoginRequest;
import com.wxm.msfast.community.common.rest.response.user.DynamicUserResponse;
import com.wxm.msfast.community.common.rest.response.user.LoginResponse;
import com.wxm.msfast.community.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-09-22 15:46:53
 */
@RestController
@Api(tags = "前台用户模块")
@RequestMapping("community/fruser")
public class FrUserController {

    @Autowired
    private FrUserService frUserService;

    @ApiOperation("验证码登陆")
    @ApiOperationSort(value = 1)
    @PostMapping("/sms/login")
    @AuthIgnore
    public R<LoginUserResponse> smsLogin(@RequestBody @Valid SmsLoginRequest request) {
        return R.ok(frUserService.smsLogin(request));
    }

    @ApiOperation("首页动态用户")
    @ApiOperationSort(value = 2)
    @GetMapping("/dynamic/user")
    @AuthIgnore
    public R<List<DynamicUserResponse>> dynamicUser() {
        return R.ok(frUserService.getDynamicUser());
    }

    @ApiOperation("获取登陆用户详细信息")
    @ApiOperationSort(value = 3)
    @GetMapping("/info")
    public R<LoginResponse> info() {
        return R.ok(frUserService.info());
    }

    @ApiOperation("获取匹配提示信息")
    @ApiOperationSort(value = 4)
    @GetMapping("/message")
    public R<List<String>> message() {
        return R.ok(frUserService.message());
    }
}
