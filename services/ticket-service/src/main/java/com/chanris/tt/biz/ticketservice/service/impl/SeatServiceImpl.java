package com.chanris.tt.biz.ticketservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant;
import com.chanris.tt.biz.ticketservice.common.enums.SeatStatusEnum;
import com.chanris.tt.biz.ticketservice.dao.entity.SeatDO;
import com.chanris.tt.biz.ticketservice.dao.mapper.SeatMapper;
import com.chanris.tt.biz.ticketservice.dto.domain.RouteDTO;
import com.chanris.tt.biz.ticketservice.dto.domain.SeatTypeCountDTO;
import com.chanris.tt.biz.ticketservice.service.SeatService;
import com.chanris.tt.biz.ticketservice.service.TrainStationService;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.dto.TrainPurchaseTicketRespDTO;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.TRAIN_STATION_CARRIAGE_REMAINING_TICKET;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 座位接口层实现
 */
@Service
@RequiredArgsConstructor
public class SeatServiceImpl extends ServiceImpl<SeatMapper, SeatDO> implements SeatService {

    private final SeatMapper seatMapper;
    private final TrainStationService trainStationService;
    private final DistributedCache distributedCache;

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
    @Override
    public List<String> listAvailableSeat(String trainId, String carriageNumber, Integer seatType, String departure, String arrival) {
        // 获得列车车厢中可用的座位集合
        LambdaQueryWrapper<SeatDO> queryWrapper = Wrappers.lambdaQuery(SeatDO.class)
                .eq(SeatDO::getTrainId, trainId)
                .eq(SeatDO::getCarriageNumber, carriageNumber)
                .eq(SeatDO::getSeatType, seatType)
                .eq(SeatDO::getStartStation, departure)
                .eq(SeatDO::getEndStation, arrival)
                // 座位状态为AVAILABLE
                .eq(SeatDO::getSeatStatus, SeatStatusEnum.AVAILABLE.getCode())
                .select(SeatDO::getSeatNumber);
        List<SeatDO> seatDOList = seatMapper.selectList(queryWrapper);
        // 只保留座位号集合
        return seatDOList.stream().map(SeatDO::getSeatNumber).collect(Collectors.toList());
    }

    /**
     * 获取列车车厢余票集合
     *
     * @param trainId           列车 ID
     * @param departure         出发站
     * @param arrival           到达站
     * @param trainCarriageList 车厢编号集合
     * @return 车厢余票集合
     */
    @Override
    public List<Integer> listSeatRemainingTicket(String trainId, String departure, String arrival, List<String> trainCarriageList) {
        // key由 prefix + 列车ID + 出发站 + 终点站 组成
        String keySuffix = StrUtil.join("_", trainId, departure, arrival);
        if (distributedCache.hasKey(TRAIN_STATION_CARRIAGE_REMAINING_TICKET + keySuffix)) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) distributedCache.getInstance();
            List<Object> trainStationCarriageRemainingTicket =
                    stringRedisTemplate.opsForHash()
                            // multiGet(key, 字段集合)
                            .multiGet(TRAIN_STATION_CARRIAGE_REMAINING_TICKET + keySuffix, Arrays.asList(trainCarriageList.toArray()));
            // 在缓存中获取成功，则返回结果
            if (CollUtil.isNotEmpty(trainStationCarriageRemainingTicket)) {
                return trainStationCarriageRemainingTicket.stream().map(each -> Integer.parseInt(each.toString())).collect(Collectors.toList());
            }
        }
        // 否则，从数据库中查询
        SeatDO seatDO = SeatDO.builder()
                .trainId(Long.parseLong(trainId))
                .startStation(departure)
                .endStation(arrival)
                .build();
        return seatMapper.listSeatRemainingTicket(seatDO, trainCarriageList);
    }

    /**
     * 查询列车有余票的车厢号集合
     *
     * @param trainId      列车 ID
     * @param carriageType 车厢类型
     * @param departure    出发站
     * @param arrival      到达站
     * @return 车厢号集合
     */
    @Override
    public List<String> listUsableCarriageNumber(String trainId, Integer carriageType, String departure, String arrival) {
        // 查询列车有余票的车厢号集合
        LambdaQueryWrapper<SeatDO> queryWrapper = Wrappers.lambdaQuery(SeatDO.class)
                .eq(SeatDO::getTrainId, trainId)
                .eq(SeatDO::getSeatType, carriageType)
                .eq(SeatDO::getStartStation, departure)
                .eq(SeatDO::getEndStation, arrival)
                .eq(SeatDO::getSeatStatus, SeatStatusEnum.AVAILABLE.getCode())
                .groupBy(SeatDO::getCarriageNumber)
                .select(SeatDO::getCarriageNumber);
        List<SeatDO> seatDOList = seatMapper.selectList(queryWrapper);
        return seatDOList.stream().map(SeatDO::getCarriageNumber).collect(Collectors.toList());
    }

    /**
     * 获取列车 startStation 到 endStation 区间可用座位数量
     *
     * @param trainId      列车 ID
     * @param startStation 出发站
     * @param endStation   到达站
     * @param seatTypes    座位类型集合
     * @return 座位剩余可用集合
     */
    @Override
    public List<SeatTypeCountDTO> listSeatTypeCount(Long trainId, String startStation, String endStation, List<Integer> seatTypes) {
        return seatMapper.listSeatTypeCount(trainId, startStation, endStation, seatTypes);
    }


    /**
     * 锁定选中以及沿途车票状态
     *
     * @param trainId    列车 ID
     * @param departure  出发站
     * @param arrival    到达站
     * @param trainPurchaseTicketRespList 乘车人以及座位信息
     */
    @Override
    public void lockSeat(String trainId, String departure, String arrival, List<TrainPurchaseTicketRespDTO> trainPurchaseTicketRespList) {
        List<RouteDTO> routeList = trainStationService.listTakeoutTrainStationRoute(trainId, departure, arrival);
        trainPurchaseTicketRespList.forEach(each -> routeList.forEach(item -> {
            LambdaUpdateWrapper<SeatDO> updateWrapper = Wrappers.lambdaUpdate(SeatDO.class)
                    .eq(SeatDO::getTrainId, trainId)
                    .eq(SeatDO::getCarriageNumber, each.getCarriageNumber())
                    .eq(SeatDO::getStartStation, item.getStartStation())
                    .eq(SeatDO::getEndStation, item.getEndStation())
                    .eq(SeatDO::getSeatNumber, each.getSeatNumber());
            SeatDO updateSeatDO = SeatDO.builder()
                    .seatStatus(SeatStatusEnum.LOCKED.getCode())
                    .build();
            seatMapper.update(updateSeatDO, updateWrapper);
        }));
    }

    /**
     * 解锁选中以及沿途车牌状态
     *
     * @param trainId                     列车 ID
     * @param departure                   出发站
     * @param arrival                     到达站
     * @param trainPurchaseTicketRespList 乘车人以及座位信息
     */
    @Override
    public void unlock(String trainId, String departure, String arrival, List<TrainPurchaseTicketRespDTO> trainPurchaseTicketRespList) {
        //查询需要解锁的 Route信息
        List<RouteDTO> routeList = trainStationService.listTakeoutTrainStationRoute(trainId, departure, arrival);
        trainPurchaseTicketRespList.forEach(each -> routeList.forEach(item -> {
            LambdaUpdateWrapper<SeatDO> updateWrapper = Wrappers.lambdaUpdate(SeatDO.class)
                    .eq(SeatDO::getTrainId, trainId)
                    .eq(SeatDO::getCarriageNumber, each.getCarriageNumber())
                    .eq(SeatDO::getStartStation, item.getStartStation())
                    .eq(SeatDO::getEndStation, item.getEndStation())
                    .eq(SeatDO::getSeatNumber, each.getSeatNumber());
            // 将座位状态设为可用
            SeatDO updateSeatDO = SeatDO.builder()
                    .seatStatus(SeatStatusEnum.AVAILABLE.getCode())
                    .build();
            seatMapper.update(updateSeatDO, updateWrapper);
        }));
    }
}
