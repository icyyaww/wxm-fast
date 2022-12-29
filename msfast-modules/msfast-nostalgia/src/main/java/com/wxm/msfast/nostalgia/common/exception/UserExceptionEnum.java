package com.wxm.msfast.nostalgia.common.exception;

import com.wxm.msfast.base.common.interfaces.BaseExceptionEnumInterface;

public enum UserExceptionEnum implements BaseExceptionEnumInterface {

    SEARCH_PARAM_EMPTY_EXCEPTION(12001, "查询条件不可为空"),
    MATCHING_BEYOND_LIMIT_EXCEPTION(12002, "匹配数限额"),
    MIN_AGE_GREATER_EXCEPTION(12003, "最小年龄大于最大年龄"),
    FIRST_PHOTO_NOT_DELETE_EXCEPTION(12004, "头像不能删除");

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
