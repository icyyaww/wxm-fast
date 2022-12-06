package com.wxm.msfast.nostalgia.common.enums;

public enum EducationalTypeEnum {

    Full_Time("全日制"),
    NO_Full_Time("非全日制");
    private String desc;

    EducationalTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
