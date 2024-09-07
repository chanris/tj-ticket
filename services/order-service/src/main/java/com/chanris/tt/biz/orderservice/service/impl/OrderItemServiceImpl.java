package com.chanris.tt.biz.orderservice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanris.tt.biz.orderservice.common.enums.OrderCanalErrorCodeEnum;
import com.chanris.tt.biz.orderservice.dao.entity.OrderDO;
import com.chanris.tt.biz.orderservice.dao.entity.OrderItemDO;
import com.chanris.tt.biz.orderservice.dao.mapper.OrderItemMapper;
import com.chanris.tt.biz.orderservice.dao.mapper.OrderMapper;
import com.chanris.tt.biz.orderservice.dto.domain.OrderItemStatusReversalDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderItemQueryReqDTO;
import com.chanris.tt.biz.orderservice.dto.resp.TicketOrderPassengerDetailRespDTO;
import com.chanris.tt.biz.orderservice.service.OrderItemService;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;
import com.chanris.tt.framework.starter.convention.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 订单明细接口层实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItemDO> implements OrderItemService {

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final RedissonClient redissonClient;

    @Override
    @Transactional
    public void orderItemStatusReversal(OrderItemStatusReversalDTO requestParam) {
        LambdaQueryWrapper<OrderDO> queryWrapper = Wrappers.lambdaQuery(OrderDO.class)
                .eq(OrderDO::getOrderSn, requestParam.getOrderSn());
        OrderDO orderDO = orderMapper.selectOne(queryWrapper);
        if (orderDO == null) {
            throw new ServiceException(OrderCanalErrorCodeEnum.ORDER_CANAL_UNKNOWN_ERROR);
        }
        RLock lock = redissonClient.getLock(StrBuilder.create("order:status-reversal:order_sn_").append(requestParam.getOrderSn()).toString());
        if (!lock.tryLock()) {
            log.warn("订单重复修改状态，状态反转请求参数：{}", JSON.toJSONString(requestParam));
        }

        try {
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setStatus(requestParam.getOrderStatus());
            LambdaQueryWrapper<OrderDO> updateWrapper = Wrappers.lambdaQuery(OrderDO.class)
                    .eq(OrderDO::getOrderSn, requestParam.getOrderSn());
            int orderUpdateResult = orderMapper.update(updateOrderDO, updateWrapper);
            if (orderUpdateResult <= 0) {
                throw new ServiceException(OrderCanalErrorCodeEnum.ORDER_STATUS_REVERSAL_ERROR);
            }
            if (CollectionUtil.isNotEmpty(requestParam.getOrderItemDOList())) {
                List<OrderItemDO> orderItemDOList = requestParam.getOrderItemDOList();
                if (CollectionUtil.isNotEmpty(orderItemDOList)) {
                    orderItemDOList.forEach(o -> {
                        OrderItemDO orderItemDO = new OrderItemDO();
                        orderItemDO.setStatus(requestParam.getOrderItemStatus());
                        LambdaUpdateWrapper<OrderItemDO> orderItemUpdateWrapper = Wrappers.lambdaUpdate(OrderItemDO.class)
                                .eq(OrderItemDO::getOrderSn, requestParam.getOrderSn())
                                .eq(OrderItemDO::getRealName, o.getRealName());
                        int orderItemUpdateResult = orderItemMapper.update(orderItemDO, orderItemUpdateWrapper);
                        if (orderItemUpdateResult <= 0) {
                            throw new ServiceException(OrderCanalErrorCodeEnum.ORDER_ITEM_STATUS_REVERSAL_ERROR);
                        }
                    });
                }
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public List<TicketOrderPassengerDetailRespDTO> queryTicketItemOrderById(TicketOrderItemQueryReqDTO requestParam) {
        LambdaQueryWrapper<OrderItemDO> queryWrapper = Wrappers.lambdaQuery(OrderItemDO.class)
                .eq(OrderItemDO::getOrderSn, requestParam.getOrderSn())
                .eq(OrderItemDO::getId, requestParam.getOrderItemRecordIds());
        List<OrderItemDO> orderItemDOList = orderItemMapper.selectList(queryWrapper);
        return BeanUtil.convert(orderItemDOList, TicketOrderPassengerDetailRespDTO.class);
    }
}
