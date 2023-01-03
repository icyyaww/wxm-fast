package com.wxm.msfast.nostalgia.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.auth.DoubleAuthRequest;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.BaseInfoEditRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wxm.msfast.nostalgia.service.FrUserAuthService;


/**
 * 用户认证
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2023-01-03 16:57:02
 */
@RestController
@RequestMapping("nostalgia/fruserauth")
@Api(tags = "双重认证")
public class FrUserAuthController {
    @Autowired
    private FrUserAuthService frUserAuthService;

    @ApiOperation("提交认证")
    @ApiOperationSort(value = 1)
    @PostMapping("/add")
    public R<Void> addAurh(@RequestBody DoubleAuthRequest request) {

        frUserAuthService.addAurh(request);
        return R.ok();
    }
}
