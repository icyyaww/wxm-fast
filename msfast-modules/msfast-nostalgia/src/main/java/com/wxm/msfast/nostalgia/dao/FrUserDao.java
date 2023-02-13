package com.wxm.msfast.nostalgia.dao;

import com.wxm.msfast.nostalgia.common.rest.request.admin.user.UserPageRequest;
import com.wxm.msfast.nostalgia.common.rest.response.admin.user.UserPageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.front.fruser.LikeResponse;
import com.wxm.msfast.nostalgia.common.rest.response.front.fruser.RecommendUserInfoResponse;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@Mapper
public interface FrUserDao extends BaseMapper<FrUserEntity> {

    List<RecommendUserInfoResponse> getRecommendUserInfo(Map<String, Object> param);

    LikeResponse getPersonalLike(Integer userId);

    List<UserPageResponse> getExaminePage(UserPageRequest request);
}
