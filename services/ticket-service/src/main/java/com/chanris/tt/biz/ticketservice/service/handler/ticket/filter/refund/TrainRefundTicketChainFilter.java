package com.chanris.tt.biz.ticketservice.service.handler.ticket.filter.refund;

import com.chanris.tt.biz.ticketservice.common.enums.TicketChainMarkEnum;
import com.chanris.tt.biz.ticketservice.dto.req.RefundTicketReqDTO;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainHandler;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车车票退款过滤器
 */
public interface TrainRefundTicketChainFilter<T extends RefundTicketReqDTO> extends AbstractChainHandler<RefundTicketReqDTO> {

    @Override
    default String mark() {
        return TicketChainMarkEnum.TRAIN_REFUND_TICKET_FILTER.name();
    }
}

