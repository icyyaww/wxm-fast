package com.wxm.msfast.base.common.enums;

public enum BaseUserTypeEnum {
    SUPER_ADMIN("超级管理员");
    private String desc;

    BaseUserTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
