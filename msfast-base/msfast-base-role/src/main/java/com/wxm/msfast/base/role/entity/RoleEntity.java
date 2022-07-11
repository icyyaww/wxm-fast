package com.wxm.msfast.base.role.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxm.msfast.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-11 11:36:42
 */
@Data
@TableName("wxm_role")
public class RoleEntity extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleKey;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 是否管理员
     */
    private Integer isAdmin;
    /**
     * 是否预置（预置角色不允许编辑与修改）
     */
    private Integer isPreset;
    /**
     * 是否启用
     */
    private Integer enable;
    /**
     * 排序号
     */
    private String sort;

}
