package com.wxm.msfast.community.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.wxm.msfast.base.common.constant.ParamTypeConstants;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.community.common.rest.response.blog.BlogDetailResponse;
import com.wxm.msfast.community.common.rest.response.blog.BlogPageResponse;
import com.wxm.msfast.community.common.rest.response.blog.CommentPageResponse;
import com.wxm.msfast.community.common.rest.response.blog.ReplyResponse;
import com.wxm.msfast.community.service.FrBlogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
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
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("我的-动态列表")
    @ApiOperationSort(value = 2)
    @GetMapping("/mine/blog/page")
    public R<PageResult<BlogPageResponse>> mineBlogPage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return R.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.pathVariable, name = "blogId", value = "动态id"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("动态列表-评论列表")
    @ApiOperationSort(value = 3)
    @GetMapping("/blog/comment/page/{blogId}")
    public R<PageResult<CommentPageResponse>> blogCommentPage(
            @PathVariable Integer blogId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        PageResult<CommentPageResponse> pageResult = new PageResult<>(null);
        return R.ok(pageResult);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.pathVariable, name = "commentId", value = "评论id"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "数量", defaultValue = "10")
    })
    @ApiOperation("动态列表-评论列表-回复列表 从第二条后开始返回")
    @ApiOperationSort(value = 4)
    @GetMapping("/blog/reply/page/{commentId}")
    public R<PageResult<ReplyResponse>> blogReplyPage(
            @PathVariable Integer commentId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        PageResult<ReplyResponse> pageResult = new PageResult<>(null);
        return R.ok(pageResult);
    }
}
