package com.chanris.tt.biz.ticketservice.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 站点路线实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    /**
     * 出发站点
     */
    private String startStation;

    /**
     * 目的站点
     */
    private String endStation;
}
