package com.chanris.tt.biz.ticketservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chanris.tt.biz.ticketservice.dao.entity.SeatDO;
import com.chanris.tt.biz.ticketservice.dto.domain.SeatTypeCountDTO;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.dto.TrainPurchaseTicketRespDTO;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
public interface SeatService extends IService<SeatDO> {

    /**
     * 获得列车车厢中可用的座位集合
     *
     * @param trainId        列车 ID
     * @param carriageNumber 车厢号
     * @param seatType       座位类型
     * @param departure      出发站
     * @param arrival        到达站
     * @return 可用座位集合
     */
    List<String> listAvailableSeat(String trainId, String carriageNumber, Integer seatType, String departure, String arrival);

    /**
     * 获取列车车厢余票集合
     *
     * @param trainId           列车 ID
     * @param departure         出发站
     * @param arrival           到达站
     * @param trainCarriageList 车厢编号集合
     * @return 车厢余票集合
     */
    List<Integer> listSeatRemainingTicket(String trainId, String departure, String arrival, List<String> trainCarriageList);

    /**
     * 查询列车有余票的车厢号集合
     *
     * @param trainId      列车 ID
     * @param carriageType 车厢类型
     * @param departure    出发站
     * @param arrival      到达站
     * @return 车厢号集合
     */
    List<String> listUsableCarriageNumber(String trainId, Integer carriageType, String departure, String arrival);


    /**
     * 获取列车 startStation 到 endStation 区间可用座位数量
     *
     * @param trainId
     * @param startStation
     * @param endStation
     * @param seatTypes
     * @return
     */
    List<SeatTypeCountDTO> listSeatTypeCount(Long trainId, String startStation, String endStation, List<Integer> seatTypes);

    /**
     * 锁定选中以及沿途车票状态
     *
     * @param trainId
     * @param departure
     * @param arrival
     * @param trainPurchaseTicketRespList
     */
    void lockSeat(String trainId, String departure, String arrival, List<TrainPurchaseTicketRespDTO> trainPurchaseTicketRespList);

    /**
     * 解锁选中以及沿途车牌状态
     *
     * @param trainId
     * @param departure
     * @param arrival
     * @param trainPurchaseTicketRespList
     */
    void unlock(String trainId, String departure, String arrival, List<TrainPurchaseTicketRespDTO> trainPurchaseTicketRespList);
}
