package com.wxm.msfast.base.common.aspect;

import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.service.ICommonAspect;
import com.wxm.msfast.base.common.utils.ServletUtils;
import com.wxm.msfast.base.common.utils.SpringBeanUtils;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.common.web.domain.R;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @Author wanglei
 * @Description  接口返回的切面
 * @Date 17:23 2022/11/13
 * @Param
 * @return
 **/
@Aspect
@Component(value = "ICommonAspect")
public class CommonAspect {

    // 定义一个切入点
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void operation() {

    }

    // 前置通知
    @Before(value = "operation()")
    public void before(JoinPoint jp) {
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
            ICommonAspect iCommonAspect = SpringBeanUtils.getBean(ICommonAspect.class);
            if (iCommonAspect != null) {
                iCommonAspect.afterReturning();
            }
        }
    }

}
