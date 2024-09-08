package com.chanris.tt.biz.ticketservice.service.impl;

import com.chanris.tt.biz.ticketservice.dao.mapper.RegionMapper;
import com.chanris.tt.biz.ticketservice.dao.mapper.StationMapper;
import com.chanris.tt.biz.ticketservice.dto.req.RegionStationQueryReqDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.RegionStationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.StationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.service.RegionStationService;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@Service
@RequiredArgsConstructor
public class RegionStationServiceImpl implements RegionStationService {

    private final RegionMapper regionMapper;
    private final StationMapper stationMapper;
    private final DistributedCache distributedCache;
    private final RedissonClient redissonClient;

    @Override
    public List<RegionStationQueryRespDTO> listRegionStation(RegionStationQueryReqDTO requestParam) {
        return null;
    }

    @Override
    public List<StationQueryRespDTO> listAllStation() {
        return null;
    }
}
