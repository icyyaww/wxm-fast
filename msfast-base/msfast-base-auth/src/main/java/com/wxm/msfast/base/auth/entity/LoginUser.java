package com.wxm.msfast.base.auth.entity;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 16:48
 **/
@Data
public class LoginUser<T> {

    private Integer id;

    private T info;
}
