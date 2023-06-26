package com.wxm.base.pay.service;

import com.wxm.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxm.base.pay.common.rest.response.NotifyUrlData;
import com.wxm.base.pay.common.rest.response.PayOrderData;

public interface IWxPayService<T extends OrderSubmitRequest> {

    PayOrderData wxAppletPay(T request);

    void appletNotifyUrl(NotifyUrlData request);

    PayOrderData wxPublicPay(T request);

    void publicNotifyUrl(NotifyUrlData request);
}
