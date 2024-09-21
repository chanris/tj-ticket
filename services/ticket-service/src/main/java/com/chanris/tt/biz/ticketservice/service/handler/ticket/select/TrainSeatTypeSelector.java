package com.chanris.tt.biz.ticketservice.service.handler.ticket.select;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chanris.tt.biz.ticketservice.common.enums.VehicleSeatTypeEnum;
import com.chanris.tt.biz.ticketservice.common.enums.VehicleTypeEnum;
import com.chanris.tt.biz.ticketservice.dao.entity.TrainStationPriceDO;
import com.chanris.tt.biz.ticketservice.dao.mapper.TrainStationPriceMapper;
import com.chanris.tt.biz.ticketservice.dto.domain.PurchaseTicketPassengerDetailDTO;
import com.chanris.tt.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import com.chanris.tt.biz.ticketservice.remote.UserRemoteService;
import com.chanris.tt.biz.ticketservice.remote.dto.PassengerRespDTO;
import com.chanris.tt.biz.ticketservice.service.SeatService;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.dto.SelectSeatDTO;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.dto.TrainPurchaseTicketRespDTO;
import com.chanris.tt.framework.starter.convention.exception.RemoteException;
import com.chanris.tt.framework.starter.convention.exception.ServiceException;
import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.designpattern.strategy.AbstractStrategyChoose;
import com.chanris.tt.framework.starter.user.core.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 购票时列车座位选择器
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class TrainSeatTypeSelector {

    private final SeatService seatService;
    private final UserRemoteService userRemoteService;
    private final TrainStationPriceMapper trainStationPriceMapper;
    private final AbstractStrategyChoose abstractStrategyChoose;
    private final ThreadPoolExecutor selectSeatThreadPoolExecutor;

    // 座位选择器
    public List<TrainPurchaseTicketRespDTO> select(Integer trainType, PurchaseTicketReqDTO requestParam) {
        // 获得乘车人信息
        List<PurchaseTicketPassengerDetailDTO> passengerDetails = requestParam.getPassengers();
        // 根据座位类型分组放到map集合中
        Map<Integer, List<PurchaseTicketPassengerDetailDTO>> seatTypeMap = passengerDetails.stream()
                .collect(Collectors.groupingBy(PurchaseTicketPassengerDetailDTO::getSeatType));
        List<TrainPurchaseTicketRespDTO> actualResult = new CopyOnWriteArrayList<>();
        if (seatTypeMap.size() > 1) {
            List<Future<List<TrainPurchaseTicketRespDTO>>> futureResults = new ArrayList<>();
            seatTypeMap.forEach((seatType, passengerSeatDetails) -> {
                // 线程池参数如何设置比较合理？
                Future<List<TrainPurchaseTicketRespDTO>> completableFuture = selectSeatThreadPoolExecutor
                        // 分配座位
                        .submit(() -> distributeSeats(trainType, seatType, requestParam, passengerSeatDetails));
                futureResults.add(completableFuture);
            });
            // 并行流极端情况下有哪些坑？
            // Java 8 引入了 并行流(parallelStream), 它能够利用多核处理器的优势来加速大量数据的操作。然而，在某些极端的情况下
            // 使用并行流可能遇到一些“坑”或者问题。
            // 1. 小规模数据集：对与小的数据集，使用并行流可能不会到性能提升，因为创建线程和管理线程需要性能开销。
            // 2. 任务粒度过细并行流的开销可能会超过并行流带来的好处
            // 3. 共享状态修改：当并行流中的任务尝试修改一个临界区变量时，可能会导致竞争状态或者数据不一致
            // 4. 内存占用增加
            // 5. ForkJoinTask对象过多：当任务数量过多时，ForkJoinTask对象也会过多，导致内存占用增加，从而导致GC频繁。
            // 6. 负载不均衡：如果流中的元素处理时间差异很大，可能导致工作分配不均匀，从而不能充分利用所有核心
            // 7. 资源竞争：并行流的任务可能会导致有限资源竞争
            // 8. 调试困难
            futureResults.parallelStream().forEach(completableFuture -> {
                try {
                    actualResult.addAll(completableFuture.get());
                } catch (Exception e) {
                    throw new ServiceException("站点余票不足，请尝试更换座位类型或选择其它站点");
                }
            });
        } else {
            seatTypeMap.forEach((seatType, passengerSeatDetails) -> {
                // 聚合结果 aggregationResult
                List<TrainPurchaseTicketRespDTO> aggregationResult = distributeSeats(trainType, seatType, requestParam, passengerSeatDetails);
                actualResult.addAll(aggregationResult);
            });
        }
        if (CollUtil.isEmpty(actualResult) || !Objects.equals(actualResult.size(), passengerDetails.size())) {
            throw new ServiceException("站点余票不足，请尝试更换座位类型或选择其它站点");
        }
        List<String> passengerIds = actualResult.stream()
                .map(TrainPurchaseTicketRespDTO::getPassengerId)
                .collect(Collectors.toList());
        Result<List<PassengerRespDTO>> passengerRemoteResult;
        List<PassengerRespDTO> passengerRemoteResultList;
        try {
            passengerRemoteResult = userRemoteService.listPassengerQueryByIds(UserContext.getUsername(), passengerIds);
            if (!passengerRemoteResult.isSuccess() || CollUtil.isEmpty(passengerRemoteResultList = passengerRemoteResult.getData())) {
                throw new RemoteException("用户服务远程调用查询乘车人相关信息错误");
            }
        } catch (Throwable ex) {
            if (ex instanceof RemoteException) {
                log.error("用户服务远程调用查询乘车人相关信息错误，当前用户：{}，请求参数：{}", UserContext.getUsername(), passengerIds);
            } else {
                log.error("用户服务远程调用查询乘车人相关信息错误，当前用户：{}，请求参数：{}", UserContext.getUsername(), passengerIds, ex);
            }
            throw ex;
        }
        actualResult.forEach(each -> {
            String passengerId = each.getPassengerId();
            passengerRemoteResultList.stream()
                    .filter(item -> Objects.equals(item.getId(), passengerId))
                    .findFirst()
                    .ifPresent(passenger -> {
                        each.setIdCard(passenger.getIdCard());
                        each.setPhone(passenger.getPhone());
                        each.setUserType(passenger.getDiscountType());
                        each.setIdType(passenger.getIdType());
                        each.setRealName(passenger.getRealName());
                    });
            LambdaQueryWrapper<TrainStationPriceDO> lambdaQueryWrapper = Wrappers.lambdaQuery(TrainStationPriceDO.class)
                    .eq(TrainStationPriceDO::getTrainId, requestParam.getTrainId())
                    .eq(TrainStationPriceDO::getDeparture, requestParam.getDeparture())
                    .eq(TrainStationPriceDO::getArrival, requestParam.getArrival())
                    .eq(TrainStationPriceDO::getSeatType, each.getSeatType())
                    .select(TrainStationPriceDO::getPrice);
            TrainStationPriceDO trainStationPriceDO = trainStationPriceMapper.selectOne(lambdaQueryWrapper);
            each.setAmount(trainStationPriceDO.getPrice());
        });
        // 购买列车中间站点余票如何更新？详细查看：https://nageoffer.com/12306/question
        seatService.lockSeat(requestParam.getTrainId(), requestParam.getDeparture(), requestParam.getArrival(), actualResult);
        return actualResult;
    }

    // 根据列车类型， 座位类型，分配座位，购票请求信息，购票人的id，座位类型 进行分配座位
    private List<TrainPurchaseTicketRespDTO> distributeSeats(Integer trainType, Integer seatType, PurchaseTicketReqDTO requestParam, List<PurchaseTicketPassengerDetailDTO> passengerSeatDetails) {
        // 构建策略key
        String buildStrategyKey = VehicleTypeEnum.findNameByCode(trainType) + VehicleSeatTypeEnum.findNameByCode(seatType);
        // 创建请求对象
        SelectSeatDTO selectSeatDTO = SelectSeatDTO.builder()
                .seatType(seatType)
                .passengerSeatDetails(passengerSeatDetails)
                .requestParam(requestParam)
                .build();
        // 执行选择座位的逻辑
        try {
            return abstractStrategyChoose.chooseAndExecuteResp(buildStrategyKey, selectSeatDTO);
        } catch (ServiceException ex) {
            throw new ServiceException("当前车次列车类型暂未适配，请购买G35或G39车次");
        }
    }
}
