package com.wxm.nostalgia.service.impl;

import com.wxm.nostalgia.dao.PayOrderDao;
import com.wxm.nostalgia.entity.PayOrderEntity;
import com.wxm.nostalgia.service.PayOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("payOrderService")
public class PayOrderServiceImpl extends ServiceImpl<PayOrderDao, PayOrderEntity> implements PayOrderService {

}
