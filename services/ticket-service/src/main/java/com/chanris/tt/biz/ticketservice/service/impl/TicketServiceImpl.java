package com.chanris.tt.biz.ticketservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanris.tt.biz.ticketservice.dao.entity.TicketDO;
import com.chanris.tt.biz.ticketservice.dao.mapper.*;
import com.chanris.tt.biz.ticketservice.dto.req.CancelTicketOrderReqDTO;
import com.chanris.tt.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import com.chanris.tt.biz.ticketservice.dto.req.RefundTicketReqDTO;
import com.chanris.tt.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.RefundTicketRespDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.TicketPageQueryRespDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.TicketPurchaseRespDTO;
import com.chanris.tt.biz.ticketservice.remote.PayRemoteService;
import com.chanris.tt.biz.ticketservice.remote.TicketOrderRemoteService;
import com.chanris.tt.biz.ticketservice.remote.dto.PayInfoRespDTO;
import com.chanris.tt.biz.ticketservice.service.SeatService;
import com.chanris.tt.biz.ticketservice.service.TicketService;
import com.chanris.tt.biz.ticketservice.service.TrainStationService;
import com.chanris.tt.biz.ticketservice.service.cache.SeatMarginCacheLoader;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.select.TrainSeatTypeSelector;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.tokenbucket.TicketAvailabilityTokenBucket;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl extends ServiceImpl<TicketMapper, TicketDO> implements TicketService {

    private final TrainMapper trainMapper;
    private final TrainStationRelationMapper trainStationRelationMapper;
    private final TrainStationPriceMapper trainStationPriceMapper;
    private final DistributedCache distributedCache;
    private final TicketOrderRemoteService ticketOrderRemoteService;
    private final PayRemoteService payRemoteService;
    private final StationMapper stationMapper;
    private final SeatService seatService;
    private final TrainStationService trainStationService;
    private final TrainSeatTypeSelector trainSeatTypeSelector;
    private final SeatMarginCacheLoader seatMarginCacheLoader;
    private final AbstractChainContext<TicketPageQueryReqDTO> ticketPageQueryAbstractChainContext;
    private final AbstractChainContext<PurchaseTicketReqDTO> purchaseTicketAbstractChainContext;
    private final AbstractChainContext<RefundTicketReqDTO> refundReqDTOAbstractChainContext;
    private final RedissonClient redissonClient;
    private final ConfigurableEnvironment environment;
    private final TicketAvailabilityTokenBucket ticketAvailabilityTokenBucket;
    @Override
    public TicketPageQueryRespDTO pageListTicketQueryV1(TicketPageQueryReqDTO requestParam) {
        return null;
    }

    @Override
    public TicketPageQueryRespDTO pageListTicketQueryV2(TicketPageQueryReqDTO requestParam) {
        return null;
    }

    @Override
    public TicketPurchaseRespDTO purchaseTicketsV1(PurchaseTicketReqDTO requestParam) {
        return null;
    }

    @Override
    public TicketPurchaseRespDTO purchaseTicketsV2(PurchaseTicketReqDTO requestParam) {
        return null;
    }

    @Override
    public TicketPurchaseRespDTO executePurchaseTickets(PurchaseTicketReqDTO requestParam) {
        return null;
    }

    @Override
    public PayInfoRespDTO getPayInfo(String orderSn) {
        return null;
    }

    @Override
    public void cancelTicketOrder(CancelTicketOrderReqDTO requestParam) {

    }

    @Override
    public RefundTicketRespDTO commonTicketRefund(RefundTicketReqDTO requestParam) {
        return null;
    }
}
