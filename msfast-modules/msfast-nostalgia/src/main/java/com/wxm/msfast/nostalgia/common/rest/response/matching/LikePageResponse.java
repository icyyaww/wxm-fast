package com.wxm.msfast.nostalgia.common.rest.response.matching;

import com.wxm.msfast.base.common.enums.GenderEnum;
import com.wxm.msfast.nostalgia.common.enums.MatchingStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-05 15:08
 **/

@Data
public class LikePageResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String headPortrait;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" +
            "    FEMALE(\"女\")")
    private GenderEnum gender;

    @ApiModelProperty(value = "身高")
    private Integer height;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "匹配状态 SUCCESS(\"匹配成功\"),\n" +
            "    LIKE(\"我喜欢的\")")
    private MatchingStatusEnum matchingStatus;

    private Date birthday;
}
