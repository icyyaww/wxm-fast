package com.wxm.msfast.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.community.entity.FrBlogEntity;

import java.util.Map;

/**
 * 用户日志
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 16:29:39
 */
public interface FrBlogService extends IService<FrBlogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

