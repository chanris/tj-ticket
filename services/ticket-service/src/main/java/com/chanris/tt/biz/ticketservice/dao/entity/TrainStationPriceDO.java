package com.chanris.tt.biz.ticketservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chanris.tt.framework.starter.database.base.BaseDO;
import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车站点价格实体
 */
@Data
@TableName("t_train_station_price")
public class TrainStationPriceDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * 车次id
     */
    private Long trainId;

    /**
     * 座位类型
     */
    private Integer seatType;

    /**
     * 出发站点
     */
    private String departure;

    /**
     * 到达站点
     */
    private String arrival;

    /**
     * 车票价格
     */
    private Integer price;
}
