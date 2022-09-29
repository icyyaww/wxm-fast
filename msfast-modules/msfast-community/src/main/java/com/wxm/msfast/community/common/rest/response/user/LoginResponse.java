package com.wxm.msfast.community.common.rest.response.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-29 16:46
 **/

@Data
public class LoginResponse {

    /**
     * 登陆账号
     */
    @ApiModelProperty(value = "登陆账号")
    private String phone;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;
}
