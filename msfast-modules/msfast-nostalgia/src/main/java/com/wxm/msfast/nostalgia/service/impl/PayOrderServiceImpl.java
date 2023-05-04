package com.wxm.msfast.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.PayOrderDao;
import com.wxm.msfast.nostalgia.entity.PayOrderEntity;
import com.wxm.msfast.nostalgia.service.PayOrderService;


@Service("payOrderService")
public class PayOrderServiceImpl extends ServiceImpl<PayOrderDao, PayOrderEntity> implements PayOrderService {

}
