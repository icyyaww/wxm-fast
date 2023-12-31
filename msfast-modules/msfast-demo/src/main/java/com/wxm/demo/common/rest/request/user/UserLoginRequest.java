package com.wxm.demo.common.rest.request.user;

import com.wxm.base.auth.common.rest.request.LoginRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-07-22 15:34
 **/
@Data
public class UserLoginRequest extends LoginRequest {

    @ApiModelProperty(value = "验证码")
    private String verificationCode;
}
