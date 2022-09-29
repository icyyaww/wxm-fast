package com.wxm.msfast.community.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.wxm.msfast.base.auth.annotation.AuthIgnore;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.community.common.rest.request.user.SmsLoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.domain.R;

import javax.validation.Valid;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-09-22 15:46:53
 */
@RestController
@Api(tags = "用户模块")
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
}
