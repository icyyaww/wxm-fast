package com.wxm.msfast.community.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FrBlogController {
    @Autowired
    private FrBlogService frBlogService;

}
