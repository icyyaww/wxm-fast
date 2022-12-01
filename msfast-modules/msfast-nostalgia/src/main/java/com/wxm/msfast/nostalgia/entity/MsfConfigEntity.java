package com.wxm.msfast.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxm.msfast.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * 系统配置
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:10
 */
@Data
@TableName(value = "msf_config", autoResultMap = true)
public class MsfConfigEntity extends BaseEntity {


    /**
     * 设置编号
     */
    @TableField("code")
    private String code;
    /**
     * 系统值
     */
    @TableField("value")
    private String value;
    /**
     * 描述信息
     */
    @TableField("descr")
    private String descr;

}
