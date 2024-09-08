package com.chanris.tt.biz.ticketservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chanris.tt.framework.starter.database.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 座位实体
 */
@Data
@TableName("t_seat")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * 列车id
     */
    private Long trainId;

    /**
     * 车厢号
     */
    private String carriageNumber;

    /**
     * 座位号
     */
    private String seatNumber;

    /**
     * 座位类型
     */
    private Integer seatType;

    /**
     * 起始站
     */
    private String startStation;

    /**
     * 终点站
     */
    private String endStation;

    /**
     * 座位状态
     */
    private Integer seatStatus;

    /**
     * 车票价格
     */
    private Integer price;
}
