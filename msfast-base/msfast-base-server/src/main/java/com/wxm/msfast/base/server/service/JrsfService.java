package com.wxm.msfast.base.server.service;

import com.wxm.msfast.base.server.entity.BaseModel;
import com.wxm.msfast.base.server.mapper.JrsfMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;

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
        BeanUtils.copyProperties(request, model);
        save(model);
    }

    private void save(M m) {
        jrsfMapper.insert(m);
    }

    private M getObjectOfM() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<M> type = (Class<M>) superClass.getActualTypeArguments()[0];
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
