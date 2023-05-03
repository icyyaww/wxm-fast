package com.wxm.msfast.base.pay.service.impl;

import com.wxm.msfast.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxm.msfast.base.pay.common.rest.response.PayOrderData;
import com.wxm.msfast.base.pay.service.IWxPayService;

public class IWxPayServiceImpl <T extends OrderSubmitRequest> implements IWxPayService<T> {
    @Override
    public PayOrderData wxAppletPay(T request) {
        return null;
    }
}
