package com.wxm.msfast.nostalgia.controller.front;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxm.msfast.nostalgia.service.PayOrderService;


/**
 * 支付订单
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2023-05-04 15:34:57
 */
@RestController
@RequestMapping("nostalgia/payorder")
public class PayOrderController {
    @Autowired
    private PayOrderService payOrderService;

}
