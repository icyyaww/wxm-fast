package com.wxm.msfast.nostalgia.common.enums;

public enum SysConfigCodeEnum {

    recommendTotal("每天推荐用户总数"),
    professionSelect("行业下拉列表"),
    payMenuList("支付菜单选择")
    ;
    private String desc;

    SysConfigCodeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
