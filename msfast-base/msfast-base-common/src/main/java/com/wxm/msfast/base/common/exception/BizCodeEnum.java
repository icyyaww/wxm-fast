package com.wxm.msfast.base.common.exception;


public enum BizCodeEnum {
    //错误状态码规范 两位业务场景+三位错误码 例如：10 通用场景 000 系统未知异常
    UNKNOWN_EXCEPTION(10000, "系统未知错误"),
    VALID_EXCEPTION(10001, "参数校验异常"),
    SERVICE_EXCEPTION(10002, "业务异常");


    private Integer code;
    private String msg;

    private BizCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
