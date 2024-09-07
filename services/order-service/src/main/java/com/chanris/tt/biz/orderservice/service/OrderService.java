package com.chanris.tt.biz.orderservice.service;

import com.chanris.tt.biz.orderservice.dto.domain.OrderStatusReversalDTO;
import com.chanris.tt.biz.orderservice.dto.req.CancelTicketOrderReqDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderCreateReqDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderPageQueryReqDTO;
import com.chanris.tt.biz.orderservice.dto.req.TicketOrderSelfPageQueryReqDTO;
import com.chanris.tt.biz.orderservice.dto.resp.TicketOrderDetailRespDTO;
import com.chanris.tt.biz.orderservice.dto.resp.TicketOrderDetailSelfRespDTO;
import com.chanris.tt.biz.orderservice.mq.event.PayResultCallbackOrderEvent;
import com.chanris.tt.framework.starter.convention.page.PageResponse;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 订单接口层
 */
public interface OrderService {

    /**
     * 根据订单号查询车票订单
     *
     * @param orderSn
     * @return
     */
    TicketOrderDetailRespDTO queryTicketOrderByOrderSn(String orderSn);

    /**
     * 根据用户名分页查询车票订单
     *
     * @param requestParam
     */
    PageResponse<TicketOrderDetailRespDTO> pageTicketOrder(TicketOrderPageQueryReqDTO requestParam);

    /**
     * 创建火车票订单
     *
     * @param requestParam 创建火车票订单入参
     * @return
     */
    String createTicketOrder(TicketOrderCreateReqDTO requestParam);

    /**
     * 关闭火车票订单
     *
     * @param requestParam 关闭火车票订单入参
     */
    boolean closeTickOrder(CancelTicketOrderReqDTO requestParam);

    /**
     * 取消火车票订单
     *
     * @param requestParam 取消火车票订单入参
     */
    boolean cancelTickOrder(CancelTicketOrderReqDTO requestParam);

    /**
     * 订单状态反转
     *
     * @param requestParam 请求参数
     */
    void statusReversal(OrderStatusReversalDTO requestParam);

    /**
     * 支付结果回调订单
     *
     * @param requestParam 请求参数
     */
    void payCallbackOrder(PayResultCallbackOrderEvent requestParam);

    /**
     * 查询本人车票订单
     *
     * @param requestParam 请求参数
     * @return 本人车票订单集合
     */
    PageResponse<TicketOrderDetailSelfRespDTO> pageSelfTicketOrder(TicketOrderSelfPageQueryReqDTO requestParam);
}
