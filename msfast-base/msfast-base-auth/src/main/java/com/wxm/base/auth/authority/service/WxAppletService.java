package com.wxm.base.auth.authority.service;

import com.wxm.base.auth.common.rest.response.WxAppletOpenResponse;

public interface WxAppletService {

    WxAppletOpenResponse getOpenIdInfoByCode(String code);
}
