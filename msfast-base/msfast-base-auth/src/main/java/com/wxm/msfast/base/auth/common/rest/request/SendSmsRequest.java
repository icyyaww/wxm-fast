package com.wxm.msfast.base.auth.common.rest.request;

import com.wxm.msfast.base.auth.common.enums.MessageType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-23 15:31
 **/

@Data
public class SendSmsRequest {

    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号格式不正确")
    private String phone;

    @NotNull
    private MessageType messageType;
}
