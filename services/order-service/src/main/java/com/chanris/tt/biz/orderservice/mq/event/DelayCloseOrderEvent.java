package com.chanris.tt.biz.orderservice.mq.event;

import com.chanris.tt.biz.orderservice.dto.req.TicketOrderItemCreateReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 延迟关闭订单事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DelayCloseOrderEvent {

    /**
     * 车次 ID
     */
    private String trainId;

    /**
     * 出发站点
     */
    private String departure;

    /**
     * 到达站点
     */
    private String arrival;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 乘车人购票信息
     */
    private List<TicketOrderItemCreateReqDTO> trainPurchaseTicketResults;
}
