package com.wxm.msfast.base.pay.service;

import com.wxm.msfast.base.pay.common.rest.request.OrderSubmitRequest;

import java.util.Map;

public interface MsfWxPayService<R extends OrderSubmitRequest> {
    Map<String, String> wxAppletPay(R request)  throws Exception;
}
