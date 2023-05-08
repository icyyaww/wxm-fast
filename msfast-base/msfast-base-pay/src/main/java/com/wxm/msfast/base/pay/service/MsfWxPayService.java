package com.wxm.msfast.base.pay.service;

import com.wxm.msfast.base.pay.common.rest.request.OrderSubmitRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface MsfWxPayService<R extends OrderSubmitRequest> {
    Map<String, String> wxAppletPay(R request)  throws Exception;

    String wxPayNotifyUrl(HttpServletRequest request, HttpServletResponse response,String platform);

    Map<String, String> wxPublic(R request)  throws Exception;

}
