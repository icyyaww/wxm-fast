package com.wxm.msfast.base.role.authority;

import com.wxm.msfast.base.auth.authority.service.IAdminAuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.common.entity.LoginUser;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-13 10:41
 **/
@Service
public class AdminAuthority implements IAdminAuthorityService {
    @Override
    public LoginUser adminLogin(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public void logout() {

    }
}
