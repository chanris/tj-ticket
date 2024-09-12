package com.chanris.tt.biz.ticketservice.service.handler.ticket.filter.purchase;

import com.chanris.tt.biz.ticketservice.common.enums.TicketChainMarkEnum;
import com.chanris.tt.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainHandler;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车购买车票过滤器
 */
public interface TrainPurchaseTicketChainFilter <T extends PurchaseTicketReqDTO> extends AbstractChainHandler<PurchaseTicketReqDTO> {
    @Override
    default String mark() {
        return TicketChainMarkEnum.TRAIN_PURCHASE_TICKET_FILTER.name();
    }
}
