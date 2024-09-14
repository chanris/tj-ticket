package com.chanris.tt.biz.ticketservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant;
import com.chanris.tt.biz.ticketservice.common.enums.RegionStationQueryTypeEnum;
import com.chanris.tt.biz.ticketservice.dao.entity.RegionDO;
import com.chanris.tt.biz.ticketservice.dao.entity.StationDO;
import com.chanris.tt.biz.ticketservice.dao.mapper.RegionMapper;
import com.chanris.tt.biz.ticketservice.dao.mapper.StationMapper;
import com.chanris.tt.biz.ticketservice.dto.req.RegionStationQueryReqDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.RegionStationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.StationQueryRespDTO;
import com.chanris.tt.biz.ticketservice.service.RegionStationService;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import com.chanris.tt.framework.starter.cache.core.CacheLoader;
import com.chanris.tt.framework.starter.cache.toolkit.CacheUtil;
import com.chanris.tt.framework.starter.common.enums.FlagEnum;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;
import com.chanris.tt.framework.starter.convention.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.REGION_STATION;
import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.STATION_ALL;
import static com.chanris.tt.biz.ticketservice.common.constant.TTConstant.ADVANCE_TICKET_DAY;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 地区以及车站接口实现层
 */
@Service
@RequiredArgsConstructor
public class RegionStationServiceImpl implements RegionStationService {

    private final RegionMapper regionMapper;
    private final StationMapper stationMapper;
    private final DistributedCache distributedCache;
    private final RedissonClient redissonClient;

    /**
     * 查询车站&城市站点集合信息
     *
     * @param requestParam 车站&站点查询参数
     * @return 车站&站点返回数据集合
     */
    @Override
    public List<RegionStationQueryRespDTO> listRegionStation(RegionStationQueryReqDTO requestParam) {
        String key;
        // 查询所有的站点信息
        if (StrUtil.isNotBlank(requestParam.getName())) {
            key = REGION_STATION + requestParam.getName();
            return safeGetRegionStation(key,
                    // 实现 cache loader
                    () -> {
                        LambdaQueryWrapper<StationDO> queryWrapper = Wrappers.lambdaQuery(StationDO.class)
                                .likeRight(StationDO::getName, requestParam.getName())
                                .or()
                                .likeRight(StationDO::getSpell, requestParam.getName());
                        List<StationDO> stationDOList = stationMapper.selectList(queryWrapper);
                        return JSON.toJSONString(BeanUtil.convert(stationDOList, RegionStationQueryRespDTO.class));
                    },
                    // 查询参数
                    requestParam.getName());
        }
        /**
         * queryType: region[popularFlag]
         */
        if (requestParam.getQueryType() == null) throw new ClientException("查询失败，查询类型和名称不能同时为空");
        key = REGION_STATION + requestParam.getQueryType();
        // 构建 queryWrapper 用于 cache loader
        LambdaQueryWrapper<RegionDO> queryWrapper = switch (requestParam.getQueryType()) {
            // queryType = 0, then 查询 region.popularFlag = true
            case 0 -> Wrappers.lambdaQuery(RegionDO.class)
                    .eq(RegionDO::getPopularFlag, FlagEnum.TRUE.code());
            // queryType = 1, then 查询 region.initial in [A,...,E]
            case 1 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.A_E.getSpells());
            // queryType = 2, then 查询 region.initial in [F,...,J]
            case 2 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.F_J.getSpells());
            // queryType = 2, then 查询 region.initial in [K,...,O]
            case 3 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.K_O.getSpells());
            // queryType = 4, then 查询 region.initial in [P,...,T]
            case 4 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.P_T.getSpells());
            // queryType = 5, then 查询 region.initial in [U,...,Z]
            case 5 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.U_Z.getSpells());
            // 其他字符串，抛异常
            default -> throw new ClientException("查询失败，请检查查询参数是否正确");
        };
        return safeGetRegionStation(
                key,
                // cache loader: 当在分布式缓存中未找到数据，则调用 cache loader的loader方法
                () -> {
                    List<RegionDO> regionDOList = regionMapper.selectList(queryWrapper);
                    return JSON.toJSONString(BeanUtil.convert(regionDOList, RegionStationQueryRespDTO.class));
                },
                String.valueOf(requestParam.getQueryType())
        );
    }

    /**
     * 查询所有车站&城市站点集合信息
     *
     * @return 车站返回数据集合
     */
    @Override
    public List<StationQueryRespDTO> listAllStation() {
        return distributedCache.safeGet(
                // key
                STATION_ALL,
                // 返回类型
                List.class,
                // cache loader
                () -> BeanUtil.convert(stationMapper.selectList(Wrappers.emptyWrapper()), StationQueryRespDTO.class),
                // 缓存过期时间
                ADVANCE_TICKET_DAY,
                // 时间单位
                TimeUnit.DAYS
        );
    }

    /**
     * 获得站点信息
     *
     * @param key
     * @param loader
     * @param param
     * @return
     */
    private List<RegionStationQueryRespDTO> safeGetRegionStation(final String key, CacheLoader<String> loader, String param) {
        List<RegionStationQueryRespDTO> result;
        // 根据 key 查询分布式缓存，有数据直接返回
        if (CollUtil.isNotEmpty(result = JSON.parseArray(distributedCache.get(key, String.class), RegionStationQueryRespDTO.class))) {
            return result;
        }
        // 获取查询站点列表锁
        String lockKey = String.format(RedisKeyConstant.LOCK_QUERY_REGION_STATION_LIST, param);
        RLock lock = redissonClient.getLock(lockKey);
        // 同步阻塞获取锁
        lock.lock();
        try {
            // 再尝试一次获取数据
            if (CollUtil.isEmpty(result = JSON.parseArray(distributedCache.get(key, String.class), RegionStationQueryRespDTO.class))) {
                // 还是没有，那么加载数据到分布式缓存，并返回数据
                if (CollUtil.isEmpty(result = loadAndSet(key, loader))) {
                    // 数据库中也没有，说明没有该数据，返回空集合
                    return Collections.emptyList();
                }
            }
        } finally {
            lock.unlock();
        }
        return result;
    }

    private List<RegionStationQueryRespDTO> loadAndSet(final String key, CacheLoader<String> loader) {
        String result = loader.load();
        // 数据库中也没有，说明没有该数据，返回空集合
        if (CacheUtil.isNullOrBlank(result)) {
            return Collections.emptyList();
        }
        List<RegionStationQueryRespDTO> respDTOList = JSON.parseArray(result, RegionStationQueryRespDTO.class);
        // 更新缓存
        distributedCache.put(
                key,
                result,
                ADVANCE_TICKET_DAY,
                TimeUnit.DAYS
        );
        return respDTOList;
    }
}
