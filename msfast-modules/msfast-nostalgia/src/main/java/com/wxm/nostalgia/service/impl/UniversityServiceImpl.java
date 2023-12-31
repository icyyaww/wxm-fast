package com.wxm.nostalgia.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxm.base.auth.service.MsfConfigService;
import com.wxm.nostalgia.common.enums.SysConfigCodeEnum;
import com.wxm.nostalgia.common.rest.response.front.profession.ProfessionResponse;
import com.wxm.nostalgia.common.rest.response.front.university.UniversityListResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.nostalgia.dao.UniversityDao;
import com.wxm.nostalgia.entity.UniversityEntity;
import com.wxm.nostalgia.service.UniversityService;

import java.util.List;


@Service("universityService")
public class UniversityServiceImpl extends ServiceImpl<UniversityDao, UniversityEntity> implements UniversityService {

    @Autowired
    MsfConfigService msfConfigService;

    @Override
    public List<UniversityListResponse> nameSelect(String name) {

        return this.baseMapper.nameSelect(name + "%");
    }

    @Override
    public List<ProfessionResponse> professionSelect() {

        String value = msfConfigService.getValueByCode(SysConfigCodeEnum.professionSelect.name());
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, ProfessionResponse.class);
        }
        return null;
    }
}
