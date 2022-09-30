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

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-09-22 15:46:53
 */
@Mapper
public interface FrUserDao extends BaseMapper<FrUserEntity> {

    @Select("select fu.id,fu.nick_name,fu.head_portrait from fr_user fu " +
            "where fu.del_flag=0 and fu.status='ENABLE' and fu.gender!=#{gender} and id!=#{id} and fu.head_portrait is not null " +
            "order by fu.create_time desc")
    List<DynamicUserResponse> getDynamicUser(@Param("gender") GenderEnum gender, @Param("id")Integer id);
}
