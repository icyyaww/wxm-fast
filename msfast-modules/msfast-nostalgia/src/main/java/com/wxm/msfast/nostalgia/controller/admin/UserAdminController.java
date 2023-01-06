package com.wxm.msfast.nostalgia.controller.admin;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.response.admin.user.UserExamineRequest;
import com.wxm.msfast.nostalgia.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-06 16:41
 **/
@RestController
@RequestMapping("admin/user")
@Api(tags = "后台-用户管理")
public class UserAdminController {

    @Autowired
    FrUserService frUserService;

    @ApiOperation("用户资料审核")
    @ApiOperationSort(value = 1)
    @PutMapping("/examine")
    public R<Void> examine(@RequestBody UserExamineRequest request) {

        frUserService.examine(request);
        return R.ok();
    }
}
