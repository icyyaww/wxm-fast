package com.wxm.msfast.nostalgia.common.rest.response.university;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UniversityListResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

}