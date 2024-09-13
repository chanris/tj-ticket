package com.chanris.tt.biz.ticketservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chanris.tt.biz.ticketservice.dao.entity.CarriageDO;
import com.chanris.tt.biz.ticketservice.dao.mapper.CarriageMapper;
import com.chanris.tt.biz.ticketservice.service.CarriageService;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import com.chanris.tt.framework.starter.cache.core.CacheLoader;
import com.chanris.tt.framework.starter.cache.toolkit.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.TRAIN_CARRIAGE;
import static com.chanris.tt.biz.ticketservice.common.constant.RedisKeyConstant.LOCK_QUERY_CARRIAGE_NUMBER_LIST;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 列车车厢接口层实现
 */
@Service
@RequiredArgsConstructor
public class CarriageServiceImpl implements CarriageService {

    private final DistributedCache distributedCache;
    private final CarriageMapper carriageMapper;
    private final RedissonClient redissonClient;

    /**
     * 查询列车车厢号集合
     *
     * @param trainId 列车ID
     * @param carriageType 车厢类型
     * @return 车厢号集合
     */
    @Override
    public List<String> listCarriageNumber(String trainId, Integer carriageType) {
        // 获得车厢号集合
        final String key = TRAIN_CARRIAGE + trainId;
        return sageGetCarriageNumber(
                // 列车 ID
                trainId,
                // key
                key,
                // 车厢类型
                carriageType,
                // cache loader
                () -> {
                    LambdaQueryWrapper<CarriageDO> queryWrapper = Wrappers.lambdaQuery(CarriageDO.class)
                            .eq(CarriageDO::getTrainId, trainId)
                            .eq(CarriageDO::getCarriageType, carriageType);
                    List<CarriageDO> carriageDOList = carriageMapper.selectList(queryWrapper);
                    // 结果只返回车厢号
                    List<String> carriageListWithOnlyNumber = carriageDOList.stream().map(CarriageDO::getCarriageNumber).collect(Collectors.toList());
                    return StrUtil.join(StrUtil.COMMA, carriageListWithOnlyNumber);
                });
    }

    /**
     * 获得缓存操作对象
     * @return
     */
    private HashOperations<String, Object, Object> getHashOperations() {
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) distributedCache.getInstance();
        return stringRedisTemplate.opsForHash();
    }

    /**
     * 尝试从分布式缓存中获取车厢号
     *
     * @param key           列车 ID
     * @param carriageType  车厢类型
     * @return
     */
    private String getCarriageNumber(final String key, Integer carriageType) {
        HashOperations<String, Object, Object> hashOperations = getHashOperations();
        return Optional.ofNullable(hashOperations.get(key, String.valueOf(carriageType))).map(Object::toString).orElse("");
    }

    private List<String> sageGetCarriageNumber(String trainId, final String key, Integer carriageType, CacheLoader<String> loader) {
        // 尝试从分布式缓存中获取车厢号
        String result = getCarriageNumber(key, carriageType);
        // 获取成功，转换为集合返回结果
        if (!CacheUtil.isNullOrBlank(result)) {
            return StrUtil.split(result, StrUtil.COMMA);
        }
        // 获得分布式锁
        RLock lock = redissonClient.getLock(String.format(LOCK_QUERY_CARRIAGE_NUMBER_LIST, trainId));
        lock.lock();
        try {
            // 再次尝试获取数据，减轻压力
            if (CacheUtil.isNullOrBlank(result = getCarriageNumber(key, carriageType))) {
                // 获取失败，更新缓存数据
                if (CacheUtil.isNullOrBlank(result = loadAndSet(carriageType, key, loader))) {
                    return Collections.emptyList();
                }
            }
        } finally {
            lock.unlock();
        }
        return StrUtil.split(result, StrUtil.COMMA);
    }

    private String loadAndSet(Integer carriageType, final String key, CacheLoader<String> loader) {
        String result = loader.load();
        if (CacheUtil.isNullOrBlank(result)) {
            return result;
        }
        HashOperations<String, Object, Object> hashOperations = getHashOperations();
        hashOperations.putIfAbsent(key, String.valueOf(carriageType), result);
        return result;
    }
}
