package com.chanris.tt.biz.ticketservice.dto.req;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 地区&站点查询请求入参
 */
@Data
public class RegionStationQueryReqDTO {

    /**
     * 查询方式
     */
    private Integer queryType;

    /**
     * 名称
     */
    private String name;
}
