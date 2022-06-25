package com.wxm.msfast.demo.entity;

import com.wxm.msfast.base.server.entity.BaseModel;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-25 15:50
 **/
@Data
public class User extends BaseModel {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}

