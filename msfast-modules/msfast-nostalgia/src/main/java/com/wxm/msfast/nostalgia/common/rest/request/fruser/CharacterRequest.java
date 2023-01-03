package com.wxm.msfast.nostalgia.common.rest.request.fruser;

import com.wxm.msfast.nostalgia.common.enums.CharacterTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-03 11:19
 **/

@Data
public class CharacterRequest {

    @ApiModelProperty(value = "外向分数")
    @NotNull
    @Max(10)
    @Min(0)
    private Integer extroversionScore;

    @ApiModelProperty(value = "想像分数")
    @NotNull
    @Max(10)
    @Min(0)
    private Integer imagineScore;

    @ApiModelProperty(value = "感性分数")
    @NotNull
    @Max(10)
    @Min(0)
    private Integer perceptualScore;

    @ApiModelProperty(value = "计划分数")
    @NotNull
    @Max(10)
    @Min(0)
    private Integer planScore;
}
