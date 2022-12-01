package com.wxm.msfast.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxm.msfast.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * 推荐配置
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@Data
@TableName(value = "recommend_config", autoResultMap = true)
public class RecommendConfigEntity extends BaseEntity {

    /**
     * 地址
     */
    @TableField("city")
    private String city;
    /**
     * 最小年龄
     */
    @TableField("min_age")
    private Integer minAge;
    /**
     * 最大年龄
     */
    @TableField("max_age")
    private Integer maxAge;

}
