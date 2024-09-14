package com.chanris.tt.biz.ticketservice.controller;

import com.chanris.tt.biz.ticketservice.common.enums.RegionStationQueryTypeEnum;
import com.chanris.tt.biz.ticketservice.dto.req.RegionStationQueryReqDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.RegionStationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.StationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.service.RegionStationService;
import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@RestController
@RequiredArgsConstructor
public class RegionStationController {

    private final RegionStationService regionStationService;

    /**
     * 查询车站&城市站点集合信息
     */
    @GetMapping("/api/ticket-service/region-station/query")
    public Result<List<RegionStationQueryRespDTO>> listRegionStation(RegionStationQueryReqDTO requestParam) {
        return Results.success(regionStationService.listRegionStation(requestParam));
    }

    /**
     * 查询车站站点集合信息
     */
    @GetMapping("/api/ticket-service/station/all")
    public Result<List<StationQueryRespDTO>> listAllStation() {
        return Results.success(regionStationService.listAllStation());
    }
}
