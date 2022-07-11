package com.wxm.msfast.base.role.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-07-11 11:26
 **/

@Data
public class RoleAddRequest {

    /**
     * 角色名称
     */
    @NotBlank
    private String roleName;
    /**
     * 角色标识
     */
    @NotBlank
    private String roleKey;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 是否预置（预置角色不允许编辑与修改）
     */
    @NotNull
    private Boolean isPreset;
    /**
     * 是否启用
     */
    @NotNull
    private Boolean enable;
    /**
     * 排序号
     */
    private String sort;
}
