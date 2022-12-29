package com.wxm.msfast.nostalgia.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.PhotoEditRequest;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendConfigRequest;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.PersonalCenterResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.PersonalInfoResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendConfigResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendUserInfoResponse;
import com.wxm.msfast.nostalgia.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@RestController
@RequestMapping("nostalgia/fruser")
@Api(tags = "前台-用户")
public class FrUserController {

    @Autowired
    private FrUserService frUserService;


    @ApiOperation("推荐用户信息")
    @ApiOperationSort(value = 1)
    @GetMapping("/recommendUserInfo")
    @AuthIgnore
    public R<List<RecommendUserInfoResponse>> recommendUserInfo(RecommendUserRequest request) {
        return R.ok(frUserService.getRecommendUserInfo(request));
    }

    @ApiOperation("查看用户配置")
    @ApiOperationSort(value = 2)
    @GetMapping("/user/configInfo")
    public R<RecommendConfigResponse> userConfigInfo() {
        return R.ok(frUserService.getRecommendConfig());
    }

    @ApiOperation("修改用户配置")
    @ApiOperationSort(value = 3)
    @PutMapping("/update/configInfo")
    public R<Void> updateConfigInfo(@RequestBody @Valid RecommendConfigRequest request) {
        frUserService.updateConfigInfo(request);
        return R.ok();
    }

    @ApiOperation("我的-个人中心")
    @ApiOperationSort(value = 4)
    @GetMapping("/personalCenter")
    public R<PersonalCenterResponse> personalCenter() {
        return R.ok(frUserService.getPersonalCenter());
    }

    @ApiOperation("我的-个人中心-资料编辑详情")
    @ApiOperationSort(value = 5)
    @GetMapping("/personalInfo")
    public R<PersonalInfoResponse> personalInfo() {
        return R.ok(frUserService.personalInfo());
    }

    @ApiOperation("我的-个人中心-资料编辑-编辑相册")
    @ApiOperationSort(value = 6)
    @PutMapping("/photoEdit")
    public R<Void> photoEdit(@RequestBody PhotoEditRequest request) {
        frUserService.photoEdit(request);
        return R.ok();
    }
}
