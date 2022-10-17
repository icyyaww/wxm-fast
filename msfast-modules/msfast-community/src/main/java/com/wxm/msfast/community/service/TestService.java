package com.wxm.msfast.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.community.entity.FrUserEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface TestService extends IService<FrUserEntity> {

    void deleteFruser(Integer id);
}
