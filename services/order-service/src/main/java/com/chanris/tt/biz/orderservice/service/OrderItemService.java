package com.chanris.tt.biz.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chanris.tt.biz.orderservice.dao.entity.OrderItemDO;
import com.chanris.tt.biz.orderservice.dto.domain.OrderItemStatusReversalDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderItemQueryReqDTO;
import com.chanris.tt.biz.orderservice.dto.resp.TicketOrderPassengerDetailRespDTO;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description
 */
public interface OrderItemService extends IService<OrderItemDO> {
    /**
     * 子订单状态反转
     *
     * @param requestParam 请求参数
     */
    void orderItemStatusReversal(OrderItemStatusReversalDTO requestParam);

    /**
     * 根据子订单记录id查询车票子订单详情
     *
     * @param requestParam 请求参数
     */
    List<TicketOrderPassengerDetailRespDTO> queryTicketItemOrderById(TicketOrderItemQueryReqDTO requestParam);
}
