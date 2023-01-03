package com.wxm.msfast.nostalgia.common.rest.response.fruser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @ApiModelProperty(value = "外向分数")
    private CharacterResponse extroversionScore;

    @ApiModelProperty(value = "想像分数")
    private CharacterResponse imagineScore;

    @ApiModelProperty(value = "感性分数")
    private CharacterResponse perceptualScore;

    @ApiModelProperty(value = "计划分数")
    private CharacterResponse planScore;
}
