package com.wxm.msfast.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.demo.entity.FrontUserEntity;

import java.util.Map;

/**
 *
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-06 18:07:55
 */
public interface FrontUserService extends IService<FrontUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


