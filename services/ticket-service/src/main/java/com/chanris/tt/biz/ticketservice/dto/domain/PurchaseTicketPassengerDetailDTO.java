package com.chanris.tt.biz.ticketservice.dto.domain;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 购票乘车人详情实体
 */
@Data
public class PurchaseTicketPassengerDetailDTO {
    /**
     * 乘车人 ID
     */
    private String passengerId;

    /**
     * 座位类型
     */
    private Integer seatType;
}
