package com.wxm.msfast.community.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wxm.msfast.community.entity.FrBlogEntity;
import com.wxm.msfast.community.service.FrBlogService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.domain.R;


/**
 * 用户日志
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 16:29:39
 */
@RestController
@RequestMapping("community/frblog")
public class FrBlogController {
    @Autowired
    private FrBlogService frBlogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("community:frblog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = frBlogService.queryPage(params);

        return R.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("community:frblog:info")
    public R info(@PathVariable("id") Integer id){
		FrBlogEntity frBlog = frBlogService.getById(id);

        return R.ok(frBlog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("community:frblog:save")
    public R save(@RequestBody FrBlogEntity frBlog){
		frBlogService.save(frBlog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("community:frblog:update")
    public R update(@RequestBody FrBlogEntity frBlog){
		frBlogService.updateById(frBlog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("community:frblog:delete")
    public R delete(@RequestBody Integer[] ids){
		frBlogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
