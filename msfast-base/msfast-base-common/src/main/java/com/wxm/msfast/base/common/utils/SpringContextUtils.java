package com.wxm.msfast.base.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring上下文工具类
 *
 * @author: wuhb
 * @date: 2019/2/25
 */
//@Component("springContextUtils")
@ConditionalOnMissingBean(SpringContextUtils.class)
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    @SuppressWarnings("unchecked")

    public static <T> T getBean(String beanId) {

        return (T) applicationContext.getBean(beanId);

    }

    public static Map<String, Class> getAllBean() {
        Map<String, Class> map = new HashMap<>();
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String beanName : beans) {
            Class bean = applicationContext.getType(beanName);
            map.put(beanName, bean);
        }
        return map;

    }

    public static <T> T getBean(Class<T> requiredType) {

        return (T) applicationContext.getBean(requiredType);

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextUtils.applicationContext = applicationContext;

    }
}
