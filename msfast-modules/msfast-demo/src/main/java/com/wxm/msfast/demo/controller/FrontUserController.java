package com.wxm.msfast.demo.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxm.msfast.demo.entity.FrontUserEntity;
import com.wxm.msfast.demo.service.FrontUserService;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.domain.R;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-06 18:07:55
 */
@RestController
@RequestMapping("demo/frontuser")
public class FrontUserController {
    @Autowired
    private FrontUserService frontUserService;


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("demo:frontuser:info")
    public R info(@PathVariable("id") Integer id) {
        FrontUserEntity frontUser = frontUserService.getById(id);

        return R.ok(frontUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("demo:frontuser:save")
    public R save(@RequestBody FrontUserEntity frontUser) {
        frontUserService.save(frontUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("demo:frontuser:update")
    public R update(@RequestBody FrontUserEntity frontUser) {
        frontUserService.updateById(frontUser);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("demo:frontuser:delete")
    public R delete(@RequestBody Integer[] ids) {
        frontUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/versionLock/{id}")
    public R versionLock(@PathVariable Long id) {
        this.frontUserService.versionLock(id);
        return R.ok();
    }
}
