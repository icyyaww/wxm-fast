package com.wxm.msfast.base.role.authority;

import com.wxm.msfast.base.auth.authority.service.IAdminAuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.entity.LoginUser;
import com.wxm.msfast.base.common.enums.BaseUserTypeEnum;
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

        if (ConfigConstants.ROLE_ADMIN_USER_NAME().equals(loginRequest.getUsername()) && ConfigConstants.ROLE_ADMIN_PASSWORD().equals(loginRequest.getPassword())) {
            LoginUser loginUser = new LoginUser();
            loginUser.setId(0);
            loginUser.setUserType(BaseUserTypeEnum.SUPER_ADMIN);
            return loginUser;
        }
        return null;
    }

    @Override
    public void logout() {

    }
}
