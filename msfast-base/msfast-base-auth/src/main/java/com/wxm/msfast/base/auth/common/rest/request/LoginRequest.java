package com.wxm.msfast.base.auth.common.rest.request;

import com.wxm.msfast.base.auth.common.enums.LoginType;
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
    @NotBlank
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /*
     * @Author wanglei
     * @Description  登录方式
     * @Date 14:26 2022/12/5
     * @Param
     * @return
     **/
    private LoginType loginType;

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
