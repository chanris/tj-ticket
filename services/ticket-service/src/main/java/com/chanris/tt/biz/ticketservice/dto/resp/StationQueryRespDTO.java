package com.chanris.tt.biz.ticketservice.dto.resp;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 站点分页查询响应参数
 */
@Data
public class StationQueryRespDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 地区编码
     */
    private String code;

    /**
     * 拼音
     */
    private String spell;

    /**
     * 城市名称
     */
    private String regionName;
}
