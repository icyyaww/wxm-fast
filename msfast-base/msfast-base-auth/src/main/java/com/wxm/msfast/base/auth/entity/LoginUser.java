package com.wxm.msfast.base.auth.entity;

import com.wxm.msfast.base.auth.common.rest.response.AuthorityUserResponse;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 16:48
 **/
@Data
public class LoginUser {

    private Boolean success;

    private String username;
}
