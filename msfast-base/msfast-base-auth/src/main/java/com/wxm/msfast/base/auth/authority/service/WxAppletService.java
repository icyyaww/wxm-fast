package com.wxm.msfast.base.auth.authority.service;

import com.wxm.msfast.base.auth.common.rest.response.WxAppletOpenResponse;

public interface WxAppletService {

    WxAppletOpenResponse getOpenIdInfoByCode(String code);
}
