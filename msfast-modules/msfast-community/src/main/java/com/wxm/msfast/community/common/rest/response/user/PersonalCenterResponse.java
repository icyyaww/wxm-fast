package com.wxm.msfast.community.common.rest.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonalCenterResponse {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String headPortrait;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "余额")
    private Integer goldBalance;
}
