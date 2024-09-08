package com.chanris.tt.biz.ticketservice.canal;

import com.chanris.tt.biz.ticketservice.mq.event.CanalBinlogEvent;
import com.chanris.tt.biz.ticketservice.remote.TicketOrderRemoteService;
import com.chanris.tt.biz.ticketservice.service.SeatService;
import com.chanris.tt.framework.starter.designpattern.strategy.AbstractExecuteStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 订单关闭或者取消后置处理组件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCloseCacheAndTokenUpdateHandler implements AbstractExecuteStrategy<CanalBinlogEvent, Void> {
    private final TicketOrderRemoteService ticketOrderRemoteService;
    private final SeatService seatSercie;
}
