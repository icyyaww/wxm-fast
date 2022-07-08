package com.wxm.msfast.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxm.msfast.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-08 16:05:16
 */
@Data
@TableName("front_user")
public class FrontUserEntity extends BaseEntity {

    /**
     * 姓名
     */
    private String name;

}
