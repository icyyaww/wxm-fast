package com.wxm.msfast.community.common.rest.response.blog;

import com.wxm.msfast.community.common.enums.FollowStatus;
import com.wxm.msfast.community.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * @Author
 * @Description  附近动态列表
 * @Date 21:31 2022/11/18
 **/
@Data
public class NearBlogPageResponse extends BlogPageResponse {

    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" +
            "    FEMALE(\"女\")")
    private GenderEnum gender;


    @ApiModelProperty(value = "关注状态 FOLLOW(\"我关注\"),\n" +
            "    FANS(\"关注我\"),\n" +
            "    FRIEND(\"互相关注\"),\n" +
            "    STRANGER(\"陌生人\")")
    private FollowStatus followStatus;

    @ApiModelProperty(value = "ip")
    private String city;

}
