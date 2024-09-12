package com.chanris.tt.biz.ticketservice.remote.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 支付单详情信息返回参数
 */
@Data
public class PayInfoRespDTO {
    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 支付总金额
     */
    private Integer totalAmount;

    /**
     * 支付状态
     */
    private Integer status;

    /**
     * 支付时间
     */
    private Date gmtPayment;
}
