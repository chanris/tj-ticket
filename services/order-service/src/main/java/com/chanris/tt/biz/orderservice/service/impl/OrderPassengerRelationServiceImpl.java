package com.chanris.tt.biz.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanris.tt.biz.orderservice.dao.entity.OrderItemPassengerDO;
import com.chanris.tt.biz.orderservice.dao.mapper.OrderItemPassengerMapper;
import com.chanris.tt.biz.orderservice.service.OrderPassengerRelationService;
import org.springframework.stereotype.Service;


/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 乘车人订单关系接口层实现
 */
@Service
public class OrderPassengerRelationServiceImpl extends ServiceImpl<OrderItemPassengerMapper, OrderItemPassengerDO> implements OrderPassengerRelationService {
}
