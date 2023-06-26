package com.wxm.demo.common.rest.request;

import lombok.Data;


/**
 * @program: msfast
 * @description: 添加请求
 * @author: Mr.Wang
 * @create: 2022-06-08 16:31
 **/

@Data
public class UserAddRequest {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
