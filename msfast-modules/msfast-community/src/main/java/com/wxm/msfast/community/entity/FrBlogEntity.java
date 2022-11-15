package com.wxm.msfast.community.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxm.msfast.base.common.entity.BaseEntity;
import com.wxm.msfast.community.common.type.BlogImageListTypeHandler;
import lombok.Data;

import java.util.List;


/**
 * 用户日志
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 16:29:39
 */
@Data
@TableName(value = "fr_blog", autoResultMap = true)
public class FrBlogEntity extends BaseEntity {

    /**
     * 前台用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 文字内容
     */
    @TableField("content")
    private String content;
    /**
     * 图片内容
     */
    @TableField(value = "images", typeHandler = BlogImageListTypeHandler.class)
    private List<String> images;

}