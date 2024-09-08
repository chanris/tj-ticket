package com.chanris.tt.biz.ticketservice.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 席别类型实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatClassDTO {
    /**
     * 席别类型
     */
    private Integer type;

    /**
     * 席别数量
     */
    private Integer quantity;

    /**
     * 席别价格
     */
    private BigDecimal price;

    /**
     * 席别候补标识
     */
    private Boolean candidate;
}
