package com.wxm.msfast.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.auth.DoubleAuthRequest;
import com.wxm.msfast.nostalgia.common.rest.response.front.auth.AuthResponse;
import com.wxm.msfast.nostalgia.service.FrUserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

        frUserAuthService.addAuth(request);
        return R.ok();
    }

    @ApiOperation("认证材料查询")
    @ApiOperationSort(value = 2)
    @PostMapping("/info")
    public R<AuthResponse> info() {
        return R.ok(frUserAuthService.info());
    }

}
