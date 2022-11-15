package com.wxm.msfast.community.controller;

import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.community.common.rest.request.user.UserRegisterRequest;
import com.wxm.msfast.community.service.FrUserService;
import com.wxm.msfast.community.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-17 10:12
 **/

@RestController
@Api(tags = "测试接口")
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private FrUserService frUserService;

    @ApiOperation("删除前台用户")
    @ApiOperationSort(value = 1)
    @GetMapping("/delete/fruser/{id}")
    @AuthIgnore
    public R<Void> deleteFruser(@PathVariable Integer id) {
        testService.deleteFruser(id);
        return R.ok();
    }

    @ApiOperation("保存用户信息")
    @ApiOperationSort(value = 2)
    @PostMapping("/save/fruser")
    @AuthIgnore
    public R<Void> saveFruser(@RequestBody UserRegisterRequest request) {

        System.out.println("保存用户信息");
        return R.ok();
    }
}
