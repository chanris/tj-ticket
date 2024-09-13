package com.chanris.tt.biz.ticketservice.controller;

import com.chanris.tt.biz.ticketservice.dto.resp.TrainStationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.service.TrainStationService;
import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车站点控制层
 */
@RestController
@RequiredArgsConstructor
public class TrainStationController {

    private final TrainStationService trainStationService;

    /**
     * 根据列车 ID 查询站点信息
     */
    @GetMapping("/api/ticket-service/train-station/query")
    public Result<List<TrainStationQueryRespDTO>> listTrainStationQuery(String trainId) {
        return Results.success(trainStationService.listTrainStationQuery(trainId));
    }
}

