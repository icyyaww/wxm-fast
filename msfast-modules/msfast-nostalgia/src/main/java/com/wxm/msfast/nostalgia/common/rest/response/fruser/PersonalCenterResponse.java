package com.wxm.msfast.nostalgia.common.rest.response.fruser;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wxm.msfast.nostalgia.common.enums.AuthStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-27 10:10
 **/

@Data
public class PersonalCenterResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String headPortrait;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "身份认证")
    private AuthStatusEnum identityAuth;

    @ApiModelProperty(value = "学历认证")
    private AuthStatusEnum educationAuth;

    @ApiModelProperty(value = "完成度")
    private Integer completionRatio;

    @ApiModelProperty(value = "金币余额")
    private Integer goldBalance;

    @ApiModelProperty(value = "我喜欢")
    private Long iLike;

    @ApiModelProperty(value = "喜欢我")
    private Long likeMe;

    @ApiModelProperty(value = "相互喜欢")
    private Long lovers;
}
