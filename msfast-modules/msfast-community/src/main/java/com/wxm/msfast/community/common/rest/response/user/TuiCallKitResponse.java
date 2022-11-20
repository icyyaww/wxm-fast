package com.wxm.msfast.community.common.rest.response.user;

import lombok.Data;

@Data
public class TuiCallKitResponse {

    private Long sdkAppId;

    private String userId;

    private String userSig;
}
