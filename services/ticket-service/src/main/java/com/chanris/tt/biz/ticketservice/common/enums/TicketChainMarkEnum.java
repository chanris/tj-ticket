package com.chanris.tt.biz.ticketservice.common.enums;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 购票相关责任链 Mark 枚举
 */
public enum TicketChainMarkEnum {

    /**
     * 车票查询过滤器
     */
    TRAIN_QUERY_FILTER,
    /**
     * 车票购买过滤器
     */
    TRAIN_PURCHASE_TICKET_FILTER,
    /**
     * 车票退款过滤器
     */
    TRAIN_REFUND_TICKET_FILTER;
}
