package com.wxm.base.pay.service.impl;

import com.wxm.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxm.base.pay.service.IWxPayService;
import com.wxm.base.pay.common.rest.response.NotifyUrlData;
import com.wxm.base.pay.common.rest.response.PayOrderData;

public class IWxPayServiceImpl <T extends OrderSubmitRequest> implements IWxPayService<T> {
    @Override
    public PayOrderData wxAppletPay(T request) {
        return null;
    }

    @Override
    public void appletNotifyUrl(NotifyUrlData request) {

    }

    @Override
    public PayOrderData wxPublicPay(T request) {
        return null;
    }

    @Override
    public void publicNotifyUrl(NotifyUrlData request) {

    }
}
