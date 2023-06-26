package com.wxm.nostalgia.common.rest.request.fruser;

import com.wxm.base.file.annotation.FileSave;
import com.wxm.nostalgia.common.enums.PhotoEditTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private String oldUrl;

    @ApiModelProperty("新的url")
    @FileSave
    private String newUrl;

    @ApiModelProperty("编辑类型 REPLACE(\"替换\"),\n" +
            "    DELETE(\"删除\"),\n" +
            "    ADD(\"新增\")")
    @NotNull
    private PhotoEditTypeEnum photoEditType;
}
