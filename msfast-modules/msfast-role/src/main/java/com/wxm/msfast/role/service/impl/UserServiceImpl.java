package com.wxm.msfast.role.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.page.Query;

import com.wxm.msfast.role.dao.UserDao;
import com.wxm.msfast.role.entity.UserEntity;
import com.wxm.msfast.role.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

}
