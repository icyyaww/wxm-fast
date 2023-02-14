package com.wxm.msfast.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.nostalgia.common.enums.AuthStatusEnum;
import com.wxm.msfast.nostalgia.common.enums.AuthTypeEnum;
import com.wxm.msfast.nostalgia.entity.FrUserExamineEntity;


/**
 * 用户认证审核
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-28 21:20:43
 */
public interface FrUserExamineService extends IService<FrUserExamineEntity> {

    FrUserExamineEntity getExamine(Integer usreId, AuthTypeEnum authType, AuthStatusEnum authStatus);
}

