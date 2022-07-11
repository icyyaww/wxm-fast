package com.wxm.msfast.base.role.service.impl;

import com.wxm.msfast.base.common.utils.SpringBeanUtils;
import com.wxm.msfast.base.role.rest.request.RoleAddRequest;
import com.wxm.msfast.base.role.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.page.Query;

import com.wxm.msfast.base.role.dao.RoleDao;
import com.wxm.msfast.base.role.entity.RoleEntity;
import com.wxm.msfast.base.role.service.RoleService;
import org.springframework.transaction.annotation.Transactional;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void add(RoleAddRequest request) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(request, roleEntity);
        this.baseMapper.insert(roleEntity);
        IRoleService iRoleService = SpringBeanUtils.getBean(IRoleService.class);
        if (iRoleService != null) {
            iRoleService.addAfter(roleEntity);
        }
    }

}
