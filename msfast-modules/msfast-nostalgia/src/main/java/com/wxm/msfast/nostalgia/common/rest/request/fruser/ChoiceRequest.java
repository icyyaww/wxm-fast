package com.wxm.msfast.nostalgia.common.rest.request.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChoiceRequest {

    /**
     * 喜欢或是不喜欢
     */
    @ApiModelProperty(value = "喜欢或是不喜欢")
    private Boolean result;
    /**
     * 被喜欢的用户
     */
    @ApiModelProperty(value = "用户id")
    private Integer otherUser;
}
