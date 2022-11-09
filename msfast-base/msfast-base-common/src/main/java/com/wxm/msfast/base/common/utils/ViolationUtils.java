package com.wxm.msfast.base.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-07-22 17:07
 **/

public class ViolationUtils {

    public static void violation(Object object) {
        Set<ConstraintViolation<Object>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if (CollectionUtil.isNotEmpty(validate)) {
            Map<String, String> errorMap = new HashMap<>();
            validate.forEach(model -> {
                errorMap.put(model.getPropertyPath().toString(), model.getMessage());
            });
            throw new JrsfException(BaseExceptionEnum.VALID_EXCEPTION).setData(errorMap).setMsg(errorMap.values().toArray()[0].toString());
        }
    }
}
