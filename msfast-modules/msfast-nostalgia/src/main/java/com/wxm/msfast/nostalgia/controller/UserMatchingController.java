package com.wxm.msfast.nostalgia.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.constant.ParamTypeConstants;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxm.msfast.nostalgia.common.rest.response.matching.LikeMePageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.matching.LikePageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.matching.SuccessPageResponse;
import io.swagger.annotations.*;
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

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("个人中心-喜欢我的")
    @ApiOperationSort(value = 2)
    @GetMapping("/likeMe/page")
    public R<PageResult<LikeMePageResponse>> likeMePage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(userMatchingService.likeMePage(pageIndex, pageSize));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("个人中心-相互喜欢")
    @ApiOperationSort(value = 3)
    @GetMapping("/success/page")
    public R<PageResult<SuccessPageResponse>> successPage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(userMatchingService.successPage(pageIndex, pageSize));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("个人中心-我喜欢")
    @ApiOperationSort(value = 4)
    @GetMapping("/like/page")
    public R<PageResult<LikePageResponse>> likePage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(userMatchingService.likePage(pageIndex, pageSize));
    }


}
