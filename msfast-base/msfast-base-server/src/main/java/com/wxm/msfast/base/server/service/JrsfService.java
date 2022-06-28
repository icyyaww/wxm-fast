package com.wxm.msfast.base.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.MapUtils;
import com.wxm.msfast.base.server.entity.BaseModel;
import com.wxm.msfast.base.server.mapper.JrsfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-25 17:30
 **/
public class JrsfService<T, M extends BaseModel> implements IJrsfService<T, M> {

    @Autowired
    private JrsfMapper<M> jrsfMapper;


    @Transactional
    @Override
    public void add(T request) {
        M model = getObjectOfM();
        MapUtils.copyPropertiesInclude((Map<String, Object>) request, model);
        save(model);
    }

    private void save(M m) {
       // iService.save(m);
    }

    private M getObjectOfM() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<M> type = (Class<M>) superClass.getActualTypeArguments()[1];
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
