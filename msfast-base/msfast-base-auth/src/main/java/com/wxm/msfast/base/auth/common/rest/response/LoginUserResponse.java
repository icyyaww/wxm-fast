package com.wxm.msfast.base.auth.common.rest.response;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 16:48
 **/
@Data
public class LoginUserResponse {

    private AuthorityUserResponse authorityUserResponse;

    private String token;

}
