package com.wxm.msfast.base.common.enums;


import com.wxm.msfast.base.common.interfaces.BaseExceptionEnumInterface;
import io.jsonwebtoken.ExpiredJwtException;

public enum BaseExceptionEnum implements BaseExceptionEnumInterface {
    //错误状态码规范 两位业务场景+三位错误码 例如：10 通用场景 000 系统未知异常
    UNKNOWN_EXCEPTION(10000, "系统未知错误"),
    VALID_EXCEPTION(10001, "参数校验异常"),
    ILLEGAL_CHARACTER_EXCEPTION(10002, "包含非法字符"),
    NO_SERVICE_AVAILABLE_EXCEPTION(10003, "没有可用的服务"),
    LOGIN_FAIL_EXCEPTION(10004, "用户名密码错误"),
    NO_PERMISSION_EXCEPTION(10005, "没有权限"),
    NO_LOGIN_EXCEPTION(10006, "未登录"),
    TOKEN_FORMAT_EXCEPTION(10007, "Token格式错误"),
    TOKEN_ILLEGAL_EXCEPTION(10008, "Token非法"),
    TOKEN_EXPIRED_EXCEPTION(10009, "Token过期"),
    OTHER_LOGIN_EXCEPTION(10010, "其他地方登陆"),
    SERVICE_BUSY_EXCEPTION(10011, "服务繁忙"),
    OPTIMISTICLOCKER_EXCEPTION(10012, "当前请求拥挤"),
    SMS_EXCEPTION(10013, "发送短信错误");

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
