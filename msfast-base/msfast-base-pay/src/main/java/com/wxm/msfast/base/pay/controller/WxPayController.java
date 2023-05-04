package com.wxm.msfast.base.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.wxm.msfast.base.common.constant.ParamTypeConstants;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.common.utils.ViolationUtils;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxm.msfast.base.pay.service.IWxPayService;
import com.wxm.msfast.base.pay.service.MsfWxPayService;
import com.wxm.msfast.base.pay.utils.ReflexUtils;
import com.wxm.msfast.base.pay.utils.wx.sdk.WxNotifyUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/wxPay")
@Api(tags = "微信支付")
public class WxPayController {

    @Autowired
    MsfWxPayService msfWxPayService;

    @PostMapping("/wxApplet")
    @ApiOperation(value = "小程序支付")
    @ApiOperationSort(1)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestBody, name = "body", value = "{\"phone\":\"code 必填\",\"verificationCode\":\"code 必填\"}", required = true)
    })
    public R<Map<String, String>> wxAppletPay(@RequestBody @ApiIgnore String viewmodelJson) throws Exception {

        IWxPayService IAuthorityService = SpringUtils.getBean(IWxPayService.class);

        Class<? extends OrderSubmitRequest> clsViewModel = ReflexUtils.getOrderSubmitRequest(IAuthorityService);

        OrderSubmitRequest viewModel = JSONObject.parseObject(viewmodelJson, clsViewModel);

        //数据校验
        ViolationUtils.violation(viewModel);
        return R.ok(msfWxPayService.wxAppletPay(viewModel));
    }

    //微信小程序回调
    @PostMapping("/wxApplet/notifyUrl")
    public String wxNotifyUrl(HttpServletRequest request, HttpServletResponse response) {

        return msfWxPayService.wxAppletPayNotifyUrl(request,response);
    }
}
