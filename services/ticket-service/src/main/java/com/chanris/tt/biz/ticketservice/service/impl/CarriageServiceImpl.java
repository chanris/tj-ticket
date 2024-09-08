package com.chanris.tt.biz.ticketservice.service.impl;

import com.chanris.tt.biz.ticketservice.dao.mapper.CarriageMapper;
import com.chanris.tt.biz.ticketservice.service.CarriageService;
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
public class CarriageServiceImpl implements CarriageService {

    private final DistributedCache distributedCache;
    private final CarriageMapper carriageMapper;
    private final RedissonClient redissonClient;

    @Override
    public List<String> listCarriageNumber(String trainId, Integer carriageTy) {
        return null;
    }
}
