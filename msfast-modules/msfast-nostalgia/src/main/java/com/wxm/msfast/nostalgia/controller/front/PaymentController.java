package com.wxm.msfast.nostalgia.controller.front;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.response.front.payment.PayMenuResponse;
import com.wxm.msfast.nostalgia.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nostalgia/payment")
@Api(tags = "支付")
public class PaymentController {

    @Autowired
    private FrUserService frUserService;

    @ApiOperation("支付选择查询")
    @ApiOperationSort(value = 1)
    @GetMapping("/pay/menu")
    public R<PayMenuResponse> payMenu() {
        return R.ok(frUserService.payMenu());
    }
}
