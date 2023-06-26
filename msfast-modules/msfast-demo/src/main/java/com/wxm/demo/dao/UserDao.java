package com.wxm.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxm.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
}
