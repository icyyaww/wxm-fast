package com.wxm.msfast.nostalgia.common.rest.request.fruser;

import com.wxm.msfast.nostalgia.common.enums.PhotoEditTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-29 10:09
 **/

@Data
public class PhotoEditRequest {

    @ApiModelProperty("旧的url")
    @NotBlank
    private String oldUrl;

    @ApiModelProperty("新的url")
    private String newUrl;

    @ApiModelProperty("编辑类型 REPLACE(\"替换\"),\n" +
            "    DELETE(\"删除\")")
    @NotNull
    private PhotoEditTypeEnum photoEditType;
}
