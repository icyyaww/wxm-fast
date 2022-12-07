package com.wxm.msfast.base.auth.common.rest.request;

import com.wxm.msfast.base.auth.common.validtype.PhoneLogin;
import com.wxm.msfast.base.auth.common.validtype.WxAppletLogin;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 *
 * @author ruoyi
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     */
    @NotBlank(groups = {PhoneLogin.class})
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(groups = {PhoneLogin.class})
    private String password;

    /**
     * @Description: 微信code
     */
    @NotBlank(groups = {WxAppletLogin.class})
    private String code;

}
