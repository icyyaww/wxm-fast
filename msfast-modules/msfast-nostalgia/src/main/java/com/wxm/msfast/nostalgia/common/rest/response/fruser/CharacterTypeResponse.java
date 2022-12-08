package com.wxm.msfast.nostalgia.common.rest.response.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-08 17:44
 **/

@Data
public class CharacterTypeResponse {

    @ApiModelProperty(value = "性格类型名称")
    private String name;

    @ApiModelProperty(value = "性格测试值")
    private List<CharacterResponse> character;
}
