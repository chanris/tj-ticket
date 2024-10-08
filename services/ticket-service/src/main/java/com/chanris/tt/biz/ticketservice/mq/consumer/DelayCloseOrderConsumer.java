package com.chanris.tt.biz.ticketservice.mq.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.chanris.tt.biz.ticketservice.common.constant.TicketRocketMQConstant;
import com.chanris.tt.biz.ticketservice.dto.domain.RouteDTO;
import com.chanris.tt.biz.ticketservice.dto.req.CancelTicketOrderReqDTO;
import com.chanris.tt.biz.ticketservice.remote.dto.TicketOrderDetailRespDTO;
import com.chanris.tt.biz.ticketservice.mq.domain.MessageWrapper;
import com.chanris.tt.biz.ticketservice.mq.event.DelayCloseOrderEvent;
import com.chanris.tt.biz.ticketservice.remote.TicketOrderRemoteService;
import com.chanris.tt.biz.ticketservice.service.SeatService;
import com.chanris.tt.biz.ticketservice.service.TrainStationService;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.dto.TrainPurchaseTicketRespDTO;
import com.chanris.tt.biz.ticketservice.service.handler.ticket.tokenbucket.TicketAvailabilityTokenBucket;
import com.chanris.tt.biz.ticketservice.remote.dto.TicketOrderPassengerDetailRespDTO;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;
import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.idempotent.annotation.Idempotent;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentSceneEnum;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.TRAIN_STATION_REMAINING_TICKET;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = TicketRocketMQConstant.ORDER_DELAY_CLOSE_TOPIC_KEY,
        selectorExpression = TicketRocketMQConstant.ORDER_DELAY_CLOSE_TAG_KEY,
        consumerGroup = TicketRocketMQConstant.TICKET_DELAY_CLOSE_CG_KEY
)
public class DelayCloseOrderConsumer implements RocketMQListener<MessageWrapper<DelayCloseOrderEvent>> {

    private final SeatService seatService;
    private final TicketOrderRemoteService ticketOrderRemoteService;
    private final TrainStationService trainStationService;
    private final DistributedCache distributedCache;
    private final TicketAvailabilityTokenBucket ticketAvailabilityTokenBucket;

    @Value("${ticket.availability.cache-update.type:}")
    private String ticketAvailabilityCacheUpdateType;

    // 延迟发送消息，若接收到消息，则说明订单过期
    @Idempotent(
            uniqueKeyPrefix = "index12306-ticket:delay_close_order:",
            key = "#delayCloseOrderEventMessageWrapper.getKeys()+'_'+#delayCloseOrderEventMessageWrapper.hashCode()",
            type = IdempotentTypeEnum.SPEL,
            scene = IdempotentSceneEnum.MQ,
            keyTimeout = 7200L
    )
    @Override
    public void onMessage(MessageWrapper<DelayCloseOrderEvent> delayCloseOrderEventMessageWrapper) {
        log.info("[延迟关闭订单] 开始消费：{}", JSON.toJSONString(delayCloseOrderEventMessageWrapper));
        DelayCloseOrderEvent delayCloseOrderEvent = delayCloseOrderEventMessageWrapper.getMessage();
        String orderSn = delayCloseOrderEvent.getOrderSn();
        Result<Boolean> closedTickOrder;
        try {
            // 执行关闭订单 主要逻辑判断订单状态是否为“待支付”，是“待支付”，获取对应分布式锁，获取锁成功后，将订单状态改为“关闭”，返回true
            closedTickOrder = ticketOrderRemoteService.closeTickOrder(new CancelTicketOrderReqDTO(orderSn));
        } catch (Throwable ex) {
            log.error("[延迟关闭订单] 订单号：{} 远程调用订单服务失败", orderSn, ex);
            throw ex;
        }
        // 若取消单成功 且 cacheUpdateType != "binlog"（不是使用canal更新的缓存）
        if (closedTickOrder.isSuccess() && !StrUtil.equals(ticketAvailabilityCacheUpdateType, "binlog")) {
            // 关单失败，则直接返回
            if (!closedTickOrder.getData()) {
                log.info("[延迟关闭订单] 订单号：{} 用户已支付订单", orderSn);
                return;
            }
            String trainId = delayCloseOrderEvent.getTrainId();
            String departure = delayCloseOrderEvent.getDeparture();
            String arrival = delayCloseOrderEvent.getArrival();
            List<TrainPurchaseTicketRespDTO> trainPurchaseTicketResults = delayCloseOrderEvent.getTrainPurchaseTicketResults();
            try {
                // 解锁选中以及沿途车牌座位状态，将座位的状态改为 UNLOCK
                seatService.unlock(trainId, departure, arrival, trainPurchaseTicketResults);
            } catch (Throwable ex) {
                log.error("[延迟关闭订单] 订单号：{} 回滚列车DB座位状态失败", orderSn, ex);
                throw ex;
            }
            try {
                StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) distributedCache.getInstance();
                Map<Integer, List<TrainPurchaseTicketRespDTO>> seatTypeMap = trainPurchaseTicketResults.stream()
                        .collect(Collectors.groupingBy(TrainPurchaseTicketRespDTO::getSeatType));
                List<RouteDTO> routeDTOList = trainStationService.listTakeoutTrainStationRoute(trainId, departure, arrival);
                routeDTOList.forEach(each -> {
                    String keySuffix = StrUtil.join("_", trainId, each.getStartStation(), each.getEndStation());
                    seatTypeMap.forEach((seatType, trainPurchaseTicketRespDTOList) -> {
                        stringRedisTemplate.opsForHash()
                                .increment(TRAIN_STATION_REMAINING_TICKET + keySuffix, String.valueOf(seatType), trainPurchaseTicketRespDTOList.size());
                    });
                });
                TicketOrderDetailRespDTO ticketOrderDetail = BeanUtil.convert(delayCloseOrderEvent, TicketOrderDetailRespDTO.class);
                ticketOrderDetail.setPassengerDetails(BeanUtil.convert(delayCloseOrderEvent.getTrainPurchaseTicketResults(), TicketOrderPassengerDetailRespDTO.class));
                ticketAvailabilityTokenBucket.rollbackInBucket(ticketOrderDetail);
            } catch (Throwable ex) {
                log.error("[延迟关闭订单] 订单号：{} 回滚列车Cache余票失败", orderSn, ex);
                throw ex;
            }
        }
    }
}
