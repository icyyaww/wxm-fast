package com.wxm.msfast.nostalgia.service.impl;

import com.wxm.msfast.nostalgia.common.rest.response.university.UniversityListResponse;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.UniversityDao;
import com.wxm.msfast.nostalgia.entity.UniversityEntity;
import com.wxm.msfast.nostalgia.service.UniversityService;

import java.util.List;


@Service("universityService")
public class UniversityServiceImpl extends ServiceImpl<UniversityDao, UniversityEntity> implements UniversityService {

    @Override
    public List<UniversityListResponse> nameSelect(String name) {

        return this.baseMapper.nameSelect(name + "%");
    }
}
