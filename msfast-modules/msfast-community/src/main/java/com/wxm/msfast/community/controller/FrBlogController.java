package com.wxm.msfast.community.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.community.entity.FrBlogEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxm.msfast.community.service.FrBlogService;


/**
 * 用户日志
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 17:05:01
 */
@RestController
@RequestMapping("community/frblog")
@Api(tags = "前台用户动态")
public class FrBlogController {

    @Autowired
    private FrBlogService frBlogService;

    @ApiOperation("动态详情")
    @ApiOperationSort(value = 1)
    @GetMapping("/info/{id}")
    public R<FrBlogEntity> endMatching(@PathVariable Integer id) {
        return R.ok(frBlogService.getById(id));
    }
}
