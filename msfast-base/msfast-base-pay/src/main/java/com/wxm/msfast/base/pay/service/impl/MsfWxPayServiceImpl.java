package com.wxm.msfast.base.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.MsfCommonTool;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxm.msfast.base.pay.common.rest.response.PayOrderData;
import com.wxm.msfast.base.pay.config.MsfWXConfig;
import com.wxm.msfast.base.pay.service.IWxPayService;
import com.wxm.msfast.base.pay.service.MsfWxPayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@Service
public class MsfWxPayServiceImpl implements MsfWxPayService {

    private static final String wxAppletHost = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Map<String, String> wxAppletPay(OrderSubmitRequest request) throws Exception {

        String openId = null;
        if (StringUtils.isNotBlank(request.getCode())) {
            String appId = ConfigConstants.WX_APPLET_APPID();
            String secret = ConfigConstants.WX_APPLET_SECRET();
            String result = restTemplate.getForObject(wxAppletHost + "?appid=" + appId + "&secret=" + secret + "&js_code=" + request.getCode() + "&grant_type=authorization_code", String.class);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            if (errcode == null) {
                openId = jsonObject.getString("openid");
            } else {
                throw new JrsfException(BaseExceptionEnum.API_ERROR).setMsg(jsonObject.getString("errmsg"));
            }
        }

        IWxPayService iWxPayService = SpringUtils.getBean(IWxPayService.class);
        PayOrderData payOrderData = iWxPayService.wxAppletPay(request);

        MsfWXConfig config = new MsfWXConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();

        data.put("body", payOrderData.getBody());
        data.put("out_trade_no", payOrderData.getOutTradeNo());

        data.put("total_fee", String.valueOf(payOrderData.getTotalFee()));
        data.put("spbill_create_ip", MsfCommonTool.getIpAddress());
        data.put("notify_url", ConfigConstants.PAY_WX_APPLET_NOTIFY_URL());
        data.put("trade_type", "JSAPI");
        data.put("openid", openId);
        data.put("attach", payOrderData.getAttach());
        Map<String, String> resp = wxpay.unifiedOrder(data);
        if (StringUtils.isNotBlank(resp.get("prepay_id"))) {
            String prepay_id = resp.get("prepay_id"); //预支付id
            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("provider", "wxpay");
            payMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("package", "prepay_id=" + prepay_id);
            payMap.put("signType", "MD5");
            String paySign = WXPayUtil.generateSignature(payMap, config.getKey());
            payMap.put("paySign", paySign);
            return payMap;
        }
        return null;
    }
}

