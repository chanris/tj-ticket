package com.chanris.tt.biz.ticketservice.service.handler.ticket.filter.query;

import com.chanris.tt.biz.ticketservice.common.enums.TicketChainMarkEnum;
import com.chanris.tt.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainHandler;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车车票查询过滤器
 */
public interface TrainTicketQueryChainFilter<T extends TicketPageQueryReqDTO> extends AbstractChainHandler<TicketPageQueryReqDTO> {

    @Override
    default String mark() {
        return TicketChainMarkEnum.TRAIN_QUERY_FILTER.name();
    }
}
