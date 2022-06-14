package com.wxm.msfast.base.common.enums;


import com.wxm.msfast.base.common.interfaces.BaseExceptionEnumInterface;

public enum BaseExceptionEnum implements BaseExceptionEnumInterface {
    //错误状态码规范 两位业务场景+三位错误码 例如：10 通用场景 000 系统未知异常
    UNKNOWN_EXCEPTION(10000, "系统未知错误"),
    VALID_EXCEPTION(10001, "参数校验异常"),
    ILLEGAL_CHARACTER_EXCEPTION(10002, "包含非法字符");

    private Integer code;
    private String msg;

    BaseExceptionEnum(Integer code, String msg) {
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
