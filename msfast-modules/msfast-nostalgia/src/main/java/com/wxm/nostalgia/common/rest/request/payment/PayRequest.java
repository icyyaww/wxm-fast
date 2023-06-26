package com.wxm.nostalgia.common.rest.request.payment;

import com.wxm.base.pay.common.rest.request.OrderSubmitRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayRequest extends OrderSubmitRequest {

    @NotNull
    private Integer productNo;

    private Integer userId;
}
