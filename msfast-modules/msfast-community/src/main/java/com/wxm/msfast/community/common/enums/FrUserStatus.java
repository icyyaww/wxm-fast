package com.wxm.msfast.community.common.enums;

public enum FrUserStatus {
    ENABLE("启用"),
    DISABLE("禁用");
    private String desc;

    FrUserStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
