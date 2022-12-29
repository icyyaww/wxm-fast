package com.wxm.msfast.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.nostalgia.common.rest.response.profession.ProfessionResponse;
import com.wxm.msfast.nostalgia.common.rest.response.university.UniversityListResponse;
import com.wxm.msfast.nostalgia.entity.UniversityEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 大学
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-20 15:49:55
 */
public interface UniversityService extends IService<UniversityEntity> {

    List<UniversityListResponse> nameSelect(String name);

    List<ProfessionResponse> professionSelect();
}

