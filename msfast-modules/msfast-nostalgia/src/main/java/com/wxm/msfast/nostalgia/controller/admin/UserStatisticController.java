package com.wxm.msfast.nostalgia.controller.admin;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.response.admin.statistic.OutlineResponse;
import com.wxm.msfast.nostalgia.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-06 15:01
 **/

@RestController
@RequestMapping("admin/statistic")
@Api(tags = "后台-用户管理")
public class UserStatisticController {

    @Autowired
    FrUserService frUserService;

    @ApiOperation("用户概要统计")
    @ApiOperationSort(value = 1)
    @GetMapping("/outline")
    public R<OutlineResponse> outline() {
        return R.ok(frUserService.outline());
    }
}
