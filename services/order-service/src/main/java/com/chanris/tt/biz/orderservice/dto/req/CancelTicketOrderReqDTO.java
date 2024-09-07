package com.chanris.tt.biz.orderservice.dto.req;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/4
 * @description 取消车票订单请求入参
 */
@Data
public class CancelTicketOrderReqDTO {

    /**
     * 订单号
     */
    private String orderSn;
}
