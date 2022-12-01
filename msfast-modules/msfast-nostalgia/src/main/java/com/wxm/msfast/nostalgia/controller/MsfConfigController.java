package com.wxm.msfast.nostalgia.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxm.msfast.nostalgia.service.MsfConfigService;


/**
 * 系统配置
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:10
 */
@RestController
@RequestMapping("nostalgia/msfconfig")
public class MsfConfigController {
    @Autowired
    private MsfConfigService msfConfigService;

}
