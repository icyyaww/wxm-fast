package com.wxm.msfast.nostalgia.common.rest.request.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wxm.msfast.nostalgia.common.enums.AuthTypeEnum;
import com.wxm.msfast.nostalgia.common.handle.UserAuthImgListHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-03 17:18
 **/

@Data
public class DoubleAuthRequest {

    @ApiModelProperty(value = "认证类别 IdentityAuth(\"实名认证\"),\n" +
            "    EducationAuth(\"学历认证\")")
    @NotNull
    private AuthTypeEnum authType;

    @ApiModelProperty(value = "材料")
    @NotEmpty
    private List<String> imgList;
}
