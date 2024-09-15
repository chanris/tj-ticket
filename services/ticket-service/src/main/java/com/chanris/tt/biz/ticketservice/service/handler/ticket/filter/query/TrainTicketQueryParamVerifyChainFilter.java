package com.chanris.tt.biz.ticketservice.service.handler.ticket.filter.query;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chanris.tt.biz.ticketservice.dao.entity.RegionDO;
import com.chanris.tt.biz.ticketservice.dao.entity.StationDO;
import com.chanris.tt.biz.ticketservice.dao.mapper.RegionMapper;
import com.chanris.tt.biz.ticketservice.dao.mapper.StationMapper;
import com.chanris.tt.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import com.chanris.tt.framework.starter.convention.exception.ClientException;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.LOCK_QUERY_ALL_REGION_LIST;
import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.QUERY_ALL_REGION_LIST;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 查询列车车票流程过滤器之验证数据是否正确
 */
@Component
@RequiredArgsConstructor
public class TrainTicketQueryParamVerifyChainFilter implements TrainTicketQueryChainFilter<TicketPageQueryReqDTO> {

    private final RegionMapper regionMapper;
    private final StationMapper stationMapper;
    private final DistributedCache distributedCache;
    private final RedissonClient redissonClient;

    /**
     * 缓存数据为空并且已经加载过标识
     */
    private static boolean CACHE_DATA_ISNULL_AND_LOAD_FLAG = false;

    @Override
    public void handler(TicketPageQueryReqDTO requestParam) {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) distributedCache.getInstance();
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        // 查询站点是否存在
        List<Object> actualExistList = hashOperations.multiGet(
                QUERY_ALL_REGION_LIST,
                ListUtil.toList(requestParam.getFromStation(), requestParam.getToStation())
        );
        // 如果存在，则成功
        long emptyCount = actualExistList.stream().filter(Objects::isNull).count();
        if (emptyCount == 0L) {
            return;
        }
        // 如果出发点或目的地不存在 或者 两个都不存在并且已经加载过缓存并且在缓存中确实有该key，则抛出异常
        if (emptyCount == 1L || (emptyCount == 2L && CACHE_DATA_ISNULL_AND_LOAD_FLAG && distributedCache.hasKey(QUERY_ALL_REGION_LIST))) {
            throw new ClientException("出发地或目的地不存在");
        }
        // 获得分布式锁
        RLock lock = redissonClient.getLock(LOCK_QUERY_ALL_REGION_LIST);
        lock.lock();
        try {
            // 如果已经加载缓存了，还找不到指定的站点，则抛出异常
            if (distributedCache.hasKey(QUERY_ALL_REGION_LIST)) {
                actualExistList = hashOperations.multiGet(
                        QUERY_ALL_REGION_LIST,
                        ListUtil.toList(requestParam.getFromStation(), requestParam.getToStation())
                );
                emptyCount = actualExistList.stream().filter(Objects::isNull).count();
                if (emptyCount > 0L) {
                    throw new ClientException("出发地或目的地不存在");
                }
                return;
            }
            // 没有加载缓存，则加载缓存
            List<RegionDO> regionDOList = regionMapper.selectList(Wrappers.emptyWrapper());
            List<StationDO> stationDOList = stationMapper.selectList(Wrappers.emptyWrapper());
            HashMap<Object, Object> regionValueMap = Maps.newHashMap();
            for (RegionDO each : regionDOList) {
                regionValueMap.put(each.getCode(), each.getName());
            }
            for (StationDO each : stationDOList) {
                regionValueMap.put(each.getCode(), each.getName());
            }
            hashOperations.putAll(QUERY_ALL_REGION_LIST, regionValueMap);
            // 已经初始化缓存，FLAG置为true
            CACHE_DATA_ISNULL_AND_LOAD_FLAG = true;
            emptyCount = regionValueMap.keySet().stream()
                    .filter(each -> StrUtil.equalsAny(each.toString(), requestParam.getFromStation(), requestParam.getToStation()))
                    .count();
            // 数据库中也没找到指定站点，抛异常
            if (emptyCount != 2L) {
                throw new ClientException("出发地或目的地不存在");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
