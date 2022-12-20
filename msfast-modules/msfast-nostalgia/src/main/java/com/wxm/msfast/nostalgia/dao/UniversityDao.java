package com.wxm.msfast.nostalgia.dao;

import com.wxm.msfast.nostalgia.common.rest.response.university.UniversityListResponse;
import com.wxm.msfast.nostalgia.entity.UniversityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 大学
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-20 15:49:55
 */
@Mapper
public interface UniversityDao extends BaseMapper<UniversityEntity> {

    List<UniversityListResponse> nameSelect(String name);
}
