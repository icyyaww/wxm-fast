package com.wxm.msfast.community.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.community.common.rest.response.blog.BlogDetailResponse;
import com.wxm.msfast.community.common.rest.response.blog.BlogPageResponse;
import com.wxm.msfast.community.common.rest.response.user.FollowPageResponse;
import com.wxm.msfast.community.entity.FrBlogEntity;
import com.wxm.msfast.community.service.FrBlogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


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
    @GetMapping("/detail/{id}")
    public R<BlogDetailResponse> detail(@PathVariable Integer id) {
        return R.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("我的-动态列表")
    @ApiOperationSort(value = 2)
    @GetMapping("/mine/blog/page")
    public R<PageResult<BlogPageResponse>> mineBlogPage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return R.ok();
    }

}
