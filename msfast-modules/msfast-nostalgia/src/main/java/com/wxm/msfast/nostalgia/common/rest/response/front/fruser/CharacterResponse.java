package com.wxm.msfast.nostalgia.common.rest.response.front.fruser;

import com.wxm.msfast.nostalgia.common.enums.CharacterPosition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-08 17:33
 **/

@Data
public class CharacterResponse {

    @ApiModelProperty(value = "分数")
    private Integer score;

    @ApiModelProperty(value = "位置 LEFT(\"左边\"),\n" +
            "    RIGHT(\"右边\")")
    private CharacterPosition position;
}
