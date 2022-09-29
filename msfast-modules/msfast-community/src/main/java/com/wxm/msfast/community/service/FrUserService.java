package com.wxm.msfast.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.community.entity.FrUserEntity;

import java.util.Map;

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-09-22 15:46:53
 */
public interface FrUserService extends IService<FrUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Long countByPhone(String phone);
}

