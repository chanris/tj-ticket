package com.chanris.tt.biz.ticketservice.service;

import com.chanris.tt.biz.ticketservice.dto.domain.RouteDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.TrainStationQueryRespDTO;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车站点接口层
 */
public interface TrainStationService {
    /**
     * 根据列车 ID 查询站点信息
     *
     * @param trainId 列车 ID
     * @return 列车经停站信息
     */
    List<TrainStationQueryRespDTO> listTrainStationQuery(String trainId);

    /**
     * 计算列车站点路线关系
     * 获取开始站点和目的站点及中间站点信息
     *
     * @param trainId   列车 ID
     * @param departure 出发站
     * @param arrival   到达站
     * @return 列车站点路线关系信息
     */
    List<RouteDTO> listTrainStationRoute(String trainId, String departure, String arrival);

    /**
     * 获取需列车站点扣减路线关系
     * 获取开始站点和目的站点、中间站点以及关联站点信息
     *
     * @param trainId   列车 ID
     * @param departure 出发站
     * @param arrival   到达站
     * @return 需扣减列车站点路线关系信息
     */
    List<RouteDTO> listTakeoutTrainStationRoute(String trainId, String departure, String arrival);
}
