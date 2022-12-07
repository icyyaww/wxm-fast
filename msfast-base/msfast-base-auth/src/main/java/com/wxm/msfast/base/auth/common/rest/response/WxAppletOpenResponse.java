package com.wxm.msfast.base.auth.common.rest.response;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-07 16:41
 **/

@Data
public class WxAppletOpenResponse {

    /*
     * @Description  登录方式为 WX_Applet 时
     **/
    private String openid; //

    /*
     * @Description  登录方式为 WX_Applet 时
     **/
    private String sessionKey;

    /*
     * @Description  登录方式为 WX_Applet 时
     **/
    private String unionId;
}
