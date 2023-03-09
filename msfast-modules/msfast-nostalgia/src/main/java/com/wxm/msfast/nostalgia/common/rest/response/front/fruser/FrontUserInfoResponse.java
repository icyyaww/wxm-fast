package com.wxm.msfast.nostalgia.common.rest.response.front.fruser;

import com.wxm.msfast.nostalgia.common.enums.MatchingStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-03-09 13:37
 **/

@Data
public class FrontUserInfoResponse extends RecommendUserInfoResponse {

    @ApiModelProperty(value = "我的匹配选择  LIKE(\"我喜欢的\"),\n" +
            "    NOT_LIKE(\"我不喜欢\"),\n")
    private MatchingStatusEnum selfMatchingStatus;

    @ApiModelProperty(value = "匹配成功 展示聊天按钮")
    private Boolean result;
}
