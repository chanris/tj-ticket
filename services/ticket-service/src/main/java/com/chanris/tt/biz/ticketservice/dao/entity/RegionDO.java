package com.chanris.tt.biz.ticketservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chanris.tt.framework.starter.database.base.BaseDO;
import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 地区表
 */
@Data
@TableName("t_region")
public class RegionDO extends BaseDO {

    /**
     * id
     */
    private Long id;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 地区编码
     */
    private String code;

    /**
     * 地区首字母
     */
    private String initial;

    /**
     * 拼音
     */
    private String spell;

    /**
     * 热门表示
     */
    private Integer popularFlag;

}
