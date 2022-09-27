package com.wxm.msfast.base.auth.common.rest.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户注册对象
 *
 * @author ruoyi
 */
@Data
public class RegisterRequest {

    @ApiModelProperty(value = "手机号码")
    private String phone;
    
    @ApiModelProperty(value = "验证码")
    private String verificationCode;
}
