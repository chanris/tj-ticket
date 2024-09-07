package com.chanris.tt.biz.orderservice.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanris.tt.biz.orderservice.common.enums.OrderStatusEnum;
import com.chanris.tt.biz.orderservice.dao.entity.OrderDO;
import com.chanris.tt.biz.orderservice.dao.entity.OrderItemDO;
import com.chanris.tt.biz.orderservice.dao.mapper.OrderItemMapper;
import com.chanris.tt.biz.orderservice.dao.mapper.OrderMapper;
import com.chanris.tt.biz.orderservice.dto.domain.OrderStatusReversalDTO;
import com.chanris.tt.biz.orderservice.dto.req.CancelTicketOrderReqDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderCreateReqDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderPageQueryReqDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderSelfPageQueryReqDTO;
import com.chanris.tt.biz.orderservice.dto.resp.TicketOrderDetailRespDTO;
import com.chanris.tt.biz.orderservice.dto.resp.TicketOrderDetailSelfRespDTO;
import com.chanris.tt.biz.orderservice.dto.resp.TicketOrderPassengerDetailRespDTO;
import com.chanris.tt.biz.orderservice.mq.event.PayResultCallbackOrderEvent;
import com.chanris.tt.biz.orderservice.mq.produce.DelayCloseOrderSendProduce;
import com.chanris.tt.biz.orderservice.remote.UserRemoteService;
import com.chanris.tt.biz.orderservice.service.OrderItemService;
import com.chanris.tt.biz.orderservice.service.OrderService;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;
import com.chanris.tt.framework.starter.convention.page.PageResponse;
import com.chanris.tt.framework.starter.database.toolkit.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 订单服务接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemService orderItemService;
    private final RedissonClient redissonClient;
    private final DelayCloseOrderSendProduce delayCloseOrderSendProduce;
    private final UserRemoteService userRemoteService;


    @Override
    public TicketOrderDetailRespDTO queryTicketOrderByOrderSn(String orderSn) {
        LambdaQueryWrapper<OrderDO> queryWrapper = Wrappers.lambdaQuery(OrderDO.class)
                .eq(OrderDO::getOrderSn, orderSn);
        OrderDO orderDO = orderMapper.selectOne(queryWrapper);
        TicketOrderDetailRespDTO result = BeanUtil.convert(orderDO, TicketOrderDetailRespDTO.class);
        LambdaQueryWrapper<OrderItemDO> orderItemQueryWrapper = Wrappers.lambdaQuery(OrderItemDO.class)
                .eq(OrderItemDO::getOrderSn, orderSn);

        List<OrderItemDO> orderItemDOList = orderItemMapper.selectList(orderItemQueryWrapper);
        result.setPassengerDetails(BeanUtil.convert(orderItemDOList, TicketOrderPassengerDetailRespDTO.class));
        return result;
    }

    @Override
    public PageResponse<TicketOrderDetailRespDTO> pageTicketOrder(TicketOrderPageQueryReqDTO requestParam) {
        LambdaQueryWrapper<OrderDO> queryWrapper = Wrappers.lambdaQuery(OrderDO.class)
                .eq(OrderDO::getUserId, requestParam.getId())
                .eq(OrderDO::getStatus, buildOrderStatusList(requestParam))
                .orderByDesc(OrderDO::getOrderTime);
        IPage<OrderDO> orderPage = orderMapper.selectPage(PageUtil.convert(requestParam), queryWrapper);
        return PageUtil.convert(orderPage, each ->  {
            TicketOrderDetailRespDTO result = BeanUtil.convert(each, TicketOrderDetailRespDTO.class);
            LambdaQueryWrapper<OrderItemDO> orderItemQueryWrapper = Wrappers.lambdaQuery(OrderItemDO.class)
                    .eq(OrderItemDO::getOrderSn, each.getOrderSn());
            List<OrderItemDO> orderItemDOList = orderItemMapper.selectList(orderItemQueryWrapper);
            result.setPassengerDetails(BeanUtil.convert(orderItemDOList, TicketOrderPassengerDetailRespDTO.class));
            return result;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTicketOrder(TicketOrderCreateReqDTO requestParam) {


        return null;
    }

    @Override
    public boolean closeTickOrder(CancelTicketOrderReqDTO requestParam) {
        return false;
    }

    @Override
    public boolean cancelTickOrder(CancelTicketOrderReqDTO requestParam) {
        return false;
    }

    @Override
    public void statusReversal(OrderStatusReversalDTO requestParam) {

    }

    @Override
    public void payCallbackOrder(PayResultCallbackOrderEvent requestParam) {

    }

    @Override
    public PageResponse<TicketOrderDetailSelfRespDTO> pageSelfTicketOrder(TicketOrderSelfPageQueryReqDTO requestParam) {
        return null;
    }

    private List<Integer> buildOrderStatusList(TicketOrderPageQueryReqDTO requestParam) {
        List<Integer> result = new ArrayList<>();
        switch (requestParam.getStatusType()) {
            case 0 -> result = ListUtil.of(
                    OrderStatusEnum.PENDING_PAYMENT.getStatus()
            );
            case 1 -> result = ListUtil.of(
                    OrderStatusEnum.ALREADY_PAID.getStatus(),
                    OrderStatusEnum.PARTIAL_REFUND.getStatus(),
                    OrderStatusEnum.FULL_REFUND.getStatus()
            );
            case 2 -> result = ListUtil.of(
                    OrderStatusEnum.COMPLETED.getStatus()
            );
        }
        return result;
    }
}
