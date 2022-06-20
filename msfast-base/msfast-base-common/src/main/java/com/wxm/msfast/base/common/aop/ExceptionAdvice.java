package com.wxm.msfast.base.common.aop;

import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.web.domain.R;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
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
        return R.fail(BaseExceptionEnum.VALID_EXCEPTION.getCode(), BaseExceptionEnum.VALID_EXCEPTION.getMessage(), errorMap);
    }

    @ExceptionHandler(value = NoSuchBeanDefinitionException.class)
    public R handleNoSuchException(NoSuchBeanDefinitionException e) {
        log.error("没有实现相关接口,接口名：{}", e.getBeanType());
        return R.fail(BaseExceptionEnum.NO_SERVICE_AVAILABLE_EXCEPTION.getCode(), BaseExceptionEnum.NO_SERVICE_AVAILABLE_EXCEPTION.getMessage(), e.getBeanType());
    }

    @ExceptionHandler(value = JrsfException.class)
    public R handleJrsfException(JrsfException e) {
        log.error("业务处理异常:", e);
        return R.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public R handleMalformedJwtException(MalformedJwtException e) {
        log.error("token 格式错误：{}", e.getMessage());
        return R.fail(BaseExceptionEnum.TOKEN_FORMAT_EXCEPTION.getCode(), BaseExceptionEnum.TOKEN_FORMAT_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = SignatureException.class)
    public R handleSignatureException(SignatureException e) {
        log.error("token 非法：{}", e.getMessage());
        return R.fail(BaseExceptionEnum.TOKEN_ILLEGAL_EXCEPTION.getCode(), BaseExceptionEnum.TOKEN_ILLEGAL_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.fail(BaseExceptionEnum.UNKNOWN_EXCEPTION.getCode(), BaseExceptionEnum.UNKNOWN_EXCEPTION.getMessage(), throwable.getMessage());
    }

}
