package com.wxm.msfast.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.community.entity.FrUserFollowEntity;

import java.util.Map;

/**
 * 前台用户关注关系
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 15:22:41
 */
public interface FrUserFollowService extends IService<FrUserFollowEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

