package com.wxm.msfast.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.nostalgia.common.enums.AuthStatusEnum;
import com.wxm.msfast.nostalgia.common.enums.AuthTypeEnum;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.FrUserExamineDao;
import com.wxm.msfast.nostalgia.entity.FrUserExamineEntity;
import com.wxm.msfast.nostalgia.service.FrUserExamineService;


@Service("frUserExamineService")
public class FrUserExamineServiceImpl extends ServiceImpl<FrUserExamineDao, FrUserExamineEntity> implements FrUserExamineService {

    @Override
    public FrUserExamineEntity getExamine(Integer usreId, AuthTypeEnum authType, AuthStatusEnum authStatus) {

        LambdaQueryWrapper<FrUserExamineEntity> queryWrapper = new QueryWrapper<FrUserExamineEntity>().lambda()
                .eq(FrUserExamineEntity::getUserId, usreId)
                .eq(FrUserExamineEntity::getAuthType, authType)
                .eq(FrUserExamineEntity::getAuthStatus, authStatus)
                .orderByDesc(FrUserExamineEntity::getId)
                .last("limit 1");
        FrUserExamineEntity userExamineEntity = this.getOne(queryWrapper);
        return userExamineEntity;
    }
}
