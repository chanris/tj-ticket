package com.chanris.tt.biz.ticketservice.service;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @descriptionc 列车车厢接口层
 */
public interface CarriageService {

    /**
     * 查询列车车厢号集合
     *
     * @param trainId 列车ID
     * @param carriageType 车厢类型
     * @return 车厢号集合
     */
    List<String> listCarriageNumber(String trainId, Integer carriageType);
}
