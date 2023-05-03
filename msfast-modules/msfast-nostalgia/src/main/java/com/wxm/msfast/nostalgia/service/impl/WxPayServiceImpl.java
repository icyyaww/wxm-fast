package com.wxm.msfast.nostalgia.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxm.msfast.base.auth.service.MsfConfigService;
import com.wxm.msfast.base.common.utils.MsfCommonTool;
import com.wxm.msfast.base.pay.common.rest.response.PayOrderData;
import com.wxm.msfast.base.pay.service.impl.IWxPayServiceImpl;
import com.wxm.msfast.nostalgia.common.enums.SysConfigCodeEnum;
import com.wxm.msfast.nostalgia.common.rest.request.payment.PayRequest;
import com.wxm.msfast.nostalgia.common.rest.response.front.payment.PayMoneyResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxPayServiceImpl extends IWxPayServiceImpl<PayRequest> {

    @Autowired
    MsfConfigService msfConfigService;

    @Override
    public PayOrderData wxAppletPay(PayRequest request) {

        String menuValue = msfConfigService.getValueByCode(SysConfigCodeEnum.payMenuList.name());
        if (StringUtils.isNotBlank(menuValue)) {
            List<PayMoneyResponse> moneyResponseList = JSON.parseArray(menuValue, PayMoneyResponse.class);
            PayMoneyResponse payMoneyResponse = moneyResponseList.stream().filter(p -> p.getPrice() != null && p.getPrice().equals(request.getProductNo())).findFirst().orElse(null);
            if (payMoneyResponse != null) {
                PayOrderData payOrderData = new PayOrderData();
                payOrderData.setBody("思君币");
                String outTradeNo = MsfCommonTool.UUID();
                payOrderData.setOutTradeNo(outTradeNo);
                payOrderData.setTotalFee(payMoneyResponse.getPrice() * 100);
                Map<String, Object> payInfo = new HashMap<>();
                payInfo.put("outTradeNo", outTradeNo);
                payInfo.put("price", payMoneyResponse.getPrice());
                payInfo.put("amount", payMoneyResponse.getAmount());
                payOrderData.setAttach(JSON.toJSONString(payInfo));
                return payOrderData;
            }
        }
        return null;
    }
}
