package com.wxm.msfast.community.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxm.msfast.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-10-10 15:45:08
 */
@Data
@TableName("sys_config")
public class SysConfigEntity extends BaseEntity {

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
