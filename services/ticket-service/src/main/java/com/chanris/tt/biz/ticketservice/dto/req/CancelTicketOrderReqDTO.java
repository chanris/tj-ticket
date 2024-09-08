package com.chanris.tt.biz.ticketservice.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 取消车票订单请求入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelTicketOrderReqDTO {

    /**
     * 订单号
     */
    private String orderSn;
}
