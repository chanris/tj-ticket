package com.chanris.tt.biz.ticketservice.dto.req;

import lombok.Data;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 车票子订单查询
 */
@Data
public class TicketOrderItemQueryReqDTO {

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 子订单记录id
     */
    private List<String> orderItemRecordIds;
}
