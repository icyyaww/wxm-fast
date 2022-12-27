package com.wxm.msfast.base.auth.authority.service;

import com.alibaba.fastjson.JSONObject;
import com.wxm.msfast.base.auth.common.rest.response.WxAppletOpenResponse;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-07 16:44
 **/

@Service
public class WxAppletServiceImpl implements WxAppletService {

    private static final String wxAppletHost = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public WxAppletOpenResponse getOpenIdInfoByCode(String code) {

        WxAppletOpenResponse response = new WxAppletOpenResponse();
        //todo 测试数据需要删除
        response.setOpenId("abc");
        response.setSessionKey("dfg");
        response.setUnionId("hjk");
        if (true) {
            return response;
        }

        if (StringUtils.isNotBlank(code)) {
            String appId = ConfigConstants.WX_APPLET_APPID();
            String secret = ConfigConstants.WX_APPLET_SECRET();
            String result = restTemplate.getForObject(wxAppletHost + "?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code", String.class);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            if (errcode == 0) {
                response.setOpenId(jsonObject.getString("openid"));
                response.setSessionKey(jsonObject.getString("session_key"));
                response.setUnionId(jsonObject.getString("unionid"));
            } else {
                throw new JrsfException(BaseExceptionEnum.API_ERROR).setMsg(jsonObject.getString("errmsg"));
            }
        }

        return response;
    }
}
