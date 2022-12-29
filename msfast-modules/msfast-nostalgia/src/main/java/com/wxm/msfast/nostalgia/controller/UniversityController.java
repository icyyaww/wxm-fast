package com.wxm.msfast.nostalgia.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendUserInfoResponse;
import com.wxm.msfast.nostalgia.common.rest.response.profession.ProfessionResponse;
import com.wxm.msfast.nostalgia.common.rest.response.university.UniversityListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wxm.msfast.nostalgia.service.UniversityService;

import java.util.List;


/**
 * 大学
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-20 15:49:55
 */
@RestController
@RequestMapping("nostalgia/university")
@Api(tags = "前台-学校")
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    @ApiOperation("学校下拉列表搜索")
    @ApiOperationSort(value = 1)
    @GetMapping("/nameSelect")
    @AuthIgnore
    public R<List<UniversityListResponse>> nameSelect(@RequestParam String name) {
        return R.ok(universityService.nameSelect(name));
    }

    @ApiOperation("行业下拉列表")
    @ApiOperationSort(value = 2)
    @GetMapping("/professionSelect")
    @AuthIgnore
    public R<List<ProfessionResponse>> professionSelect() {
        return R.ok(universityService.professionSelect());
    }
}
