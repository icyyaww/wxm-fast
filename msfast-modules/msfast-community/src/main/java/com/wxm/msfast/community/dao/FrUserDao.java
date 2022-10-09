package com.wxm.msfast.community.dao;

import com.wxm.msfast.community.common.enums.GenderEnum;
import com.wxm.msfast.community.common.rest.response.user.DynamicUserResponse;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-09-22 15:46:53
 */
@Mapper
public interface FrUserDao extends BaseMapper<FrUserEntity> {

    List<DynamicUserResponse> getDynamicUser(Map<String, Object> param);
}
