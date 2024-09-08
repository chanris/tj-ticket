package com.chanris.tt.biz.ticketservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chanris.tt.framework.starter.database.base.BaseDO;
import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 车站实体
 */
@Data
@TableName("t_station")
public class StationDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * 车站编码
     */
    private String code;

    /**
     * 车站名称
     */
    private String name;

    /**
     * 拼音
     */
    private String spell;

    /**
     * 地区编号
     */
    private String region;

    /**
     * 地区名称
     */
    private String regionName;
}
