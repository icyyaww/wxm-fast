package com.wxm.msfast.nostalgia.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.constant.ParamTypeConstants;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendUserInfoResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wxm.msfast.nostalgia.service.FrUserService;


/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@RestController
@RequestMapping("nostalgia/fruser")
@Api(tags = "前台-首页")
public class FrUserController {

    @Autowired
    private FrUserService frUserService;


    @ApiOperation("推荐用户信息")
    @ApiOperationSort(value = 1)
    @GetMapping("/recommendUserInfo")
    @AuthIgnore
    public R<RecommendUserInfoResponse> recommendUserInfo(RecommendUserRequest request) {
        return R.ok(new RecommendUserInfoResponse());
    }
}
