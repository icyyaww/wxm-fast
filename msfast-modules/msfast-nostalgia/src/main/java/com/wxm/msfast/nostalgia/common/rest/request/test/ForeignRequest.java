package com.wxm.msfast.nostalgia.common.rest.request.test;

import com.wxm.msfast.base.common.annotation.ForeignTable;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-06-20 17:49
 **/

@Data
public class ForeignRequest {

    private String name;

    @ForeignTable(value = "fr_user", message = "用户信息不存在")
    private Integer categoryId;
}
