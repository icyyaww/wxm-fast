package com.wxm.msfast.nostalgia.common.enums;

public enum AuthTypeEnum {

    InfoAuth("资料认证"),
    IdentityAuth("实名认证"),
    EducationAuth("学历认证");
    private String desc;

    AuthTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
