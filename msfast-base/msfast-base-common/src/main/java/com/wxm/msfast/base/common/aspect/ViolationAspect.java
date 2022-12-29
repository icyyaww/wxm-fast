package com.wxm.msfast.base.common.aspect;

import com.wxm.msfast.base.common.service.ICommonAspect;
import com.wxm.msfast.base.common.utils.SpringBeanUtils;
import com.wxm.msfast.base.common.utils.ViolationUtils;
import com.wxm.msfast.base.common.web.domain.R;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-29 10:33
 **/
@Aspect
@Component(value = "IViolationAspect")
public class ViolationAspect {

    // 定义一个切入点
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void operation() {

    }

    // 前置通知
    @Before(value = "operation()")
    public void before(JoinPoint jp) {
        Object[] objects = jp.getArgs();
        if (objects != null && objects.length > 0) {
            Object object = objects[0];
            ViolationUtils.violation(object);
        }
    }

    // 后置通知
    @After(value = "operation()")
    public void after(JoinPoint jp) {
    }

    // 返回通知
    @AfterReturning(value = "operation()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
    }

    // 返回通知
    @AfterReturning(value = "operation()", returning = "result")
    public void afterReturning(JoinPoint jp, R result) {
        if (result.getCode() == 200) {
        }
    }
}
