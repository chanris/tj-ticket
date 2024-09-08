package com.chanris.tt.biz.ticketservice.service.impl;

import com.chanris.tt.biz.ticketservice.dao.mapper.TrainStationMapper;
import com.chanris.tt.biz.ticketservice.dto.domain.RouteDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.TrainStationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.service.TrainStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车站点接口实现层
 */
@Service
@RequiredArgsConstructor
public class TrainStationServiceImpl implements TrainStationService {

    private final TrainStationMapper trainStationMapper;

    @Override
    public List<TrainStationQueryRespDTO> listTrainStationQuery(String trainId) {
        return null;
    }

    @Override
    public List<RouteDTO> listTrainStationRoute(String trainId, String departure, String arrival) {
        return null;
    }

    @Override
    public List<RouteDTO> listTakeoutTrainStationRoute(String trainId, String departure, String arrival) {
        return null;
    }
}
