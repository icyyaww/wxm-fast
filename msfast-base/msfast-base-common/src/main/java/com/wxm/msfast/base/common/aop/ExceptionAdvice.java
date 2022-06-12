package com.wxm.msfast.base.common.aop;

import com.wxm.msfast.base.common.exception.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: msfast
 * @description: 统一处理异常操作
 * @author: Mr.Wang
 * @create: 2022-06-12 10:18
 **/
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{},异常类型: {}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }));
        return R.error(BaseExceptionEnum.VALID_EXCEPTION.getCode(), BaseExceptionEnum.VALID_EXCEPTION.getMessage()).put("data", errorMap);
    }

    @ExceptionHandler(value = JrsfException.class)
    public R handleJrsfException(JrsfException jrsfException) {
        log.error("业务处理异常:", jrsfException);
        return R.error(jrsfException.getCode(), jrsfException.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.error(BaseExceptionEnum.UNKNOWN_EXCEPTION.getCode(), BaseExceptionEnum.UNKNOWN_EXCEPTION.getMessage());
    }

}
