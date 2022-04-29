package com.wxm.msfast.role.dao;

import com.wxm.msfast.role.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户
 * 
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-04-29 16:53:44
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
