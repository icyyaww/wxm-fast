package com.wxm.msfast.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wxm.msfast.community.dao.SysConfigDao;
import com.wxm.msfast.community.entity.SysConfigEntity;
import com.wxm.msfast.community.service.SysConfigService;


@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {


    /**
     * @param code
     * @Description: 根据code查询value
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/10 下午4:08
     */
    @Override
    public String getValueByCode(String code) {
        Wrapper<SysConfigEntity> sysConfigEntityWrapper = new QueryWrapper<SysConfigEntity>().lambda().eq(SysConfigEntity::getCode, code);
        SysConfigEntity sysConfigEntity = this.getOne(sysConfigEntityWrapper);
        if (sysConfigEntity != null) {
            return sysConfigEntity.getValue();
        }
        return null;
    }

}
