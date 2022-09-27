package com.wxm.msfast.community.common.exception;

import com.wxm.msfast.base.common.interfaces.BaseExceptionEnumInterface;

public enum UserExceptionEnum implements BaseExceptionEnumInterface {
    USER_EXIST_EXCEPTION(11001, "用户已存在"),
    USER_NOT_EXIST_EXCEPTION(11002, "用户不存在");

    private Integer code;
    private String msg;

    UserExceptionEnum(Integer code, String msg) {
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
