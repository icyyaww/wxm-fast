package com.wxm.msfast.base.auth.authority.interceptor;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wxm.msfast.base.auth.annotation.AuthIgnore;
import com.wxm.msfast.base.common.web.domain.R;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户安全验证拦截类
 *
 * @author: wuhb
 * @date: 2019/2/25
 */
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            if (((HandlerMethod) handler).getBean() instanceof BasicErrorController) return true;
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        if (annotation != null) {
            return true;
        }

        return false;
    }

    private void responseError(HttpServletResponse response, HttpStatus status, String msg) {

        R ret = R.fail(status.value(), msg);


       /* response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
            out.flush();
        } catch (IOException e) {
            EvLog.error(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }*/
    }
}
