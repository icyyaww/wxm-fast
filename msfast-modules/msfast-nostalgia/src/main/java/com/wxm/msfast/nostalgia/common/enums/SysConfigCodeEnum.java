package com.wxm.msfast.nostalgia.common.enums;

public enum SysConfigCodeEnum {

    recommendTotal("每天推荐用户总数");
    private String desc;

    SysConfigCodeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
