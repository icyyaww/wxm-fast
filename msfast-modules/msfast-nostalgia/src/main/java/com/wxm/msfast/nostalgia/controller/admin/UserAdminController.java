package com.wxm.msfast.nostalgia.controller.admin;

import com.wxm.msfast.base.common.constant.ParamTypeConstants;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.admin.user.UserExamineRequest;
import com.wxm.msfast.nostalgia.common.rest.request.admin.user.UserPageRequest;
import com.wxm.msfast.nostalgia.common.rest.response.admin.user.IdentityExamineInfoResponse;
import com.wxm.msfast.nostalgia.common.rest.response.admin.user.UserExamineInfoResponse;
import com.wxm.msfast.nostalgia.common.rest.response.admin.user.UserIdentityPageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.admin.user.UserPageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.front.matching.LikeMePageResponse;
import com.wxm.msfast.nostalgia.service.FrUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("用户资料审核列表")
    @ApiOperationSort(value = 1)
    @GetMapping("/examine/page")
    public R<PageResult<UserPageResponse>> examinePage(
            UserPageRequest request,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(frUserService.examinePage(request, pageIndex, pageSize));
    }

    @ApiOperation("用户资料审核")
    @ApiOperationSort(value = 2)
    @PutMapping("/examine")
    public R<Void> examine(@RequestBody UserExamineRequest request) {

        frUserService.examine(request);
        return R.ok();
    }

    @ApiOperation("用户资料审核详情")
    @ApiOperationSort(value = 3)
    @GetMapping("/info/{id}")
    public R<UserExamineInfoResponse> examineInfo(@PathVariable Integer id) {
        return R.ok(frUserService.getExamineInfo(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("用户身份审核列表")
    @ApiOperationSort(value = 4)
    @GetMapping("/identity/page")
    public R<PageResult<UserIdentityPageResponse>> identityPage(
            UserPageRequest request,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(frUserService.identityPage(request, pageIndex, pageSize));
    }

    @ApiOperation("用户身份审核详情")
    @ApiOperationSort(value = 5)
    @GetMapping("/identityExamine/{id}")
    public R<IdentityExamineInfoResponse> identityExamine(@PathVariable Integer id) {
        return R.ok(frUserService.identityExamine(id));
    }

    @ApiOperation("用户身份审核")
    @ApiOperationSort(value = 6)
    @PutMapping("/identity/examine")
    public R<Void> identityExamine(@RequestBody UserExamineRequest request) {

        frUserService.identityExamine(request);
        return R.ok();
    }
}
