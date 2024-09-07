package com.chanris.tt.biz.orderservice.mq.consumer;

import com.chanris.tt.biz.orderservice.common.constant.OrderRocketMQConstant;
import com.chanris.tt.biz.orderservice.common.enums.OrderItemStatusEnum;
import com.chanris.tt.biz.orderservice.common.enums.OrderStatusEnum;
import com.chanris.tt.biz.orderservice.dto.domain.OrderStatusReversalDTO;
import com.chanris.tt.biz.orderservice.mq.domain.MessageWrapper;
import com.chanris.tt.biz.orderservice.mq.event.PayResultCallbackOrderEvent;
import com.chanris.tt.biz.orderservice.service.OrderService;
import com.chanris.tt.framework.starter.idempotent.annotation.Idempotent;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 支付结果回调订单消费者
 * todo 24/9/5
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = OrderRocketMQConstant.PAY_GLOBAL_TOPIC_KEY,
        selectorExpression = OrderRocketMQConstant.PAY_RESULT_CALLBACK_TAG_KEY,
        consumerGroup = OrderRocketMQConstant.PAY_RESULT_CALLBACK_ORDER_CG_KEY
)
public class PayResultCallbackOrderConsumer implements RocketMQListener<MessageWrapper<PayResultCallbackOrderEvent>> {

    private final OrderService orderService;

    @Idempotent(
            uniqueKeyPrefix = "tt-order:pay_result_callback:",
            key = "#message.getKeys()+'_'+#message.hashCode()",
            type = IdempotentTypeEnum.SPEL,
            keyTimeout = 7200L
    )
    @Override
    public void onMessage(MessageWrapper<PayResultCallbackOrderEvent> message) {
        PayResultCallbackOrderEvent payResultCallbackOrderEvent = message.getMessage();
        OrderStatusReversalDTO orderStatusReversalDTO = OrderStatusReversalDTO.builder()
                .orderSn(payResultCallbackOrderEvent.getOrderSn())
                .orderStatus(OrderStatusEnum.ALREADY_PAID.getStatus())
                .orderItemStatus(OrderItemStatusEnum.ALREADY_PAID.getStatus())
                .build();
        orderService.statusReversal(orderStatusReversalDTO);
        orderService.payCallbackOrder(payResultCallbackOrderEvent);
    }
}
