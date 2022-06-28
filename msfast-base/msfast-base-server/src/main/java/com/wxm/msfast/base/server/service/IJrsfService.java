package com.wxm.msfast.base.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.server.entity.BaseModel;

public interface IJrsfService<T, M extends BaseModel> {

    void add(T request);

}
