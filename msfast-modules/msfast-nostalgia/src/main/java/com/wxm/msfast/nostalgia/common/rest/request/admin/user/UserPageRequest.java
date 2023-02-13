package com.wxm.msfast.nostalgia.common.rest.request.admin.user;

import com.wxm.msfast.nostalgia.common.enums.AuthStatusEnum;
import com.wxm.msfast.nostalgia.common.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-13 16:39
 **/

@Data
public class UserPageRequest {

    /**
     * 用户资料状态
     */
    @ApiModelProperty(value = "用户资料状态 EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum authStatus;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型 Dummy(\"虚拟\"),\n" +
            "    Normal(\"正常\")")
    private UserTypeEnum userType;
}
