package com.wxm.msfast.nostalgia.dao;

import com.wxm.msfast.nostalgia.common.rest.response.matching.LikeMePageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.matching.SuccessPageResponse;
import com.wxm.msfast.nostalgia.entity.UserMatchingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户匹配
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@Mapper
public interface UserMatchingDao extends BaseMapper<UserMatchingEntity> {

    List<LikeMePageResponse> getLikeMePage(Integer userId);

    List<SuccessPageResponse> getSuccessPage(Integer userId);
}
