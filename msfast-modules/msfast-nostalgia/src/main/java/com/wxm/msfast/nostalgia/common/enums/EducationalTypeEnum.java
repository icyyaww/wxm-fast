package com.wxm.msfast.nostalgia.common.enums;

public enum EducationalTypeEnum {

    FullTime("全日制"),
    NOFullTime("非全日制");
    private String desc;

    EducationalTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
