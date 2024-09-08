package com.chanris.tt.biz.ticketservice.service.handler.ticket.dto;

import com.chanris.tt.biz.ticketservice.dto.domain.PurchaseTicketPassengerDetailDTO;
import com.chanris.tt.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 选择座位实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectSeatDTO {

    /**
     * 座位类型
     */
    private Integer seatType;

    /**
     * 座位对应的乘车人集合
     */
    private List<PurchaseTicketPassengerDetailDTO> passengerSeatDetails;

    /**
     * 购票原始入股
     */
    private PurchaseTicketReqDTO requestParam;
}
