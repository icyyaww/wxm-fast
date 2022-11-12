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

import com.wxm.msfast.community.entity.FrUserFollowEntity;
import com.wxm.msfast.community.service.FrUserFollowService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.domain.R;


/**
 * 前台用户关注关系
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 15:22:41
 */
@RestController
@RequestMapping("community/fruserfollow")
public class FrUserFollowController {
    @Autowired
    private FrUserFollowService frUserFollowService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("community:fruserfollow:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = frUserFollowService.queryPage(params);

        return R.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("community:fruserfollow:info")
    public R info(@PathVariable("id") Integer id){
		FrUserFollowEntity frUserFollow = frUserFollowService.getById(id);

        return R.ok(frUserFollow);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("community:fruserfollow:save")
    public R save(@RequestBody FrUserFollowEntity frUserFollow){
		frUserFollowService.save(frUserFollow);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("community:fruserfollow:update")
    public R update(@RequestBody FrUserFollowEntity frUserFollow){
		frUserFollowService.updateById(frUserFollow);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("community:fruserfollow:delete")
    public R delete(@RequestBody Integer[] ids){
		frUserFollowService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
