package com.wxm.demo.exception;

import com.wxm.base.common.interfaces.BaseExceptionEnumInterface;

public enum DemoExceptionEnum implements BaseExceptionEnumInterface {
    AGE_MIN(11001, "未满18周岁 请在父母陪同下观看");

    private Integer code;
    private String msg;

    DemoExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
