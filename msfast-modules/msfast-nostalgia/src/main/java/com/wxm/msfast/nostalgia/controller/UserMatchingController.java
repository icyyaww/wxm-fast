package com.wxm.msfast.nostalgia.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.ChoiceRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wxm.msfast.nostalgia.service.UserMatchingService;

import javax.validation.Valid;


/**
 * 用户匹配
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@RestController
@RequestMapping("nostalgia/usermatching")
@Api(tags = "前台-匹配")
public class UserMatchingController {
    @Autowired
    private UserMatchingService userMatchingService;


    @ApiOperation("用户匹配")
    @ApiOperationSort(value = 1)
    @PostMapping("/match")
    public R<Void> match(@RequestBody @Valid ChoiceRequest request) {
        userMatchingService.match(request);
        return R.ok();
    }
}
