package com.wxm.msfast.base.auth.common.rest.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户注册对象
 *
 * @author ruoyi
 */
@Data
public class RegisterRequest {

    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号格式不正确")
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    @NotBlank
    private String verificationCode;

    @NotBlank(message = "密码不可为空")
    @Length(min = 6, max = 25, message = "密码为{min}-{max}位")
    private String password;

    @NotBlank(message = "确认密码不可为空")
    @Length(min = 6, max = 25, message = "确认密码为{min}-{max}位")
    private String truePassword;
}
