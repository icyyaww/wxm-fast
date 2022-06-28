package com.wxm.msfast.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxm.msfast.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
}
