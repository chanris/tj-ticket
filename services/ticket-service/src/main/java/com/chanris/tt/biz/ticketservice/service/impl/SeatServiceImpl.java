package com.chanris.tt.biz.ticketservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanris.tt.biz.ticketservice.dao.entity.SeatDO;
import com.chanris.tt.biz.ticketservice.dao.mapper.SeatMapper;
import com.chanris.tt.biz.ticketservice.dto.domain.SeatTypeCountDTO;
import com.chanris.tt.biz.ticketservice.service.SeatService;
import com.chanris.tt.biz.ticketservice.service.TrainStationService;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.dto.TrainPurchaseTicketRespDTO;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@Service
@RequiredArgsConstructor
public class SeatServiceImpl extends ServiceImpl<SeatMapper, SeatDO> implements SeatService {

    private final SeatMapper seatMapper;
    private final TrainStationService trainStationService;
    private final DistributedCache distributedCache;

    @Override
    public List<String> listAvailableSeat(String trainId, String carriageNumber, Integer seatType, String departure, String arrival) {
        return null;
    }

    @Override
    public List<Integer> listSeatRemainingTicket(String trainId, String departure, String arrival, List<String> trainCarriageList) {
        return null;
    }

    @Override
    public List<String> listUsableCarriageNumber(String trainId, Integer carriageType, String departure, String arrival) {
        return null;
    }

    @Override
    public List<SeatTypeCountDTO> listSeatTypeCount(Long trainId, String startStation, String endStation, List<Integer> seatTypes) {
        return null;
    }

    @Override
    public void lockSeat(String trainId, String departure, String arrival, List<TrainPurchaseTicketRespDTO> trainPurchaseTicketRespList) {

    }

    @Override
    public void unlock(String trainId, String departure, String arrival, List<TrainPurchaseTicketRespDTO> trainPurchaseTicketRespList) {

    }
}
