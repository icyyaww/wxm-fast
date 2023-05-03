package com.wxm.msfast.nostalgia.service.impl;

import com.wxm.msfast.base.common.utils.MsfCommonTool;
import com.wxm.msfast.base.pay.common.rest.response.PayOrderData;
import com.wxm.msfast.base.pay.service.impl.IWxPayServiceImpl;
import com.wxm.msfast.nostalgia.common.rest.request.payment.PayRequest;
import org.springframework.stereotype.Service;

@Service
public class WxPayServiceImpl extends IWxPayServiceImpl<PayRequest> {

    @Override
    public PayOrderData wxAppletPay(PayRequest request) {

        PayOrderData payOrderData = new PayOrderData();
        payOrderData.setBody("思君币");
        payOrderData.setOutTradeNo(MsfCommonTool.UUID());
        payOrderData.setTotalFee(1);
        payOrderData.setAttach("附加信息");
        return payOrderData;
    }
}
