package com.wxm.msfast.base.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.auth.common.enums.ConfigAccessEnum;
import com.wxm.msfast.base.auth.dao.MsfConfigDao;
import com.wxm.msfast.base.auth.entity.MsfConfigEntity;
import com.wxm.msfast.base.auth.service.MsfConfigService;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import org.springframework.stereotype.Service;


@Service("msfConfigService")
public class MsfConfigServiceImpl extends ServiceImpl<MsfConfigDao, MsfConfigEntity> implements MsfConfigService {


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
        MsfConfigEntity msfConfigEntity = this.getConfigByCode(code);
        if (msfConfigEntity != null) {
            return msfConfigEntity.getValue();
        }
        return null;
    }

    @Override
    public MsfConfigEntity getConfigByCode(String code) {
        Wrapper<MsfConfigEntity> sysConfigEntityWrapper = new QueryWrapper<MsfConfigEntity>().lambda().eq(MsfConfigEntity::getCode, code);
        MsfConfigEntity msfConfigEntity = this.getOne(sysConfigEntityWrapper);
        if (msfConfigEntity != null) {

            if (ConfigAccessEnum.PRIVATE.equals(msfConfigEntity.getAccess())) {
                Integer owner = TokenUtils.getOwnerId();
                if (owner == null) {
                    throw new JrsfException(BaseExceptionEnum.NO_PERMISSION_EXCEPTION);
                }
            }
        }
        return msfConfigEntity;
    }

}
