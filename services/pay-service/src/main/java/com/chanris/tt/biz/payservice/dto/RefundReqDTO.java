package com.chanris.tt.biz.payservice.dto;

import com.chanris.tt.biz.payservice.common.enums.RefundTypeEnum;
import com.chanris.tt.biz.payservice.remote.dto.TicketOrderPassengerDetailRespDTO;
import lombok.Data;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 退款请求入参数实体
 */
@Data
public class RefundReqDTO {

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 退款类型枚举
     */
    private RefundTypeEnum refundTypeEnum;

    /**
     * 退款金额
     */
    private Integer refundAmount;

    /**
     * 部分退款车票详情集合
     */
    private List<TicketOrderPassengerDetailRespDTO> refundDetailReqDTOList;
}
