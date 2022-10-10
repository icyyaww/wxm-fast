package com.wxm.msfast.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.community.entity.SysConfigEntity;

import java.util.Map;

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-10-10 15:45:08
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description: 根据code查询value
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/10 下午4:08
     */
    String getValueByCode(String code);
}

