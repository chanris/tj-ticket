package com.chanris.tt.biz.orderservice.service.orderid;

import com.chanris.tt.framework.starter.cache.DistributedCache;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/7
 * @description 订单 ID 全局唯一生成器管理
 */
@Component
@RequiredArgsConstructor
public class OrderIdGeneratorManager implements InitializingBean {

    private final RedissonClient redissonClient;
    private final DistributedCache distributedCache;
    private static DistributedIdGenerator DISTRIBUTED_ID_GENERATOR;

    @Override
    public void afterPropertiesSet() throws Exception {
       String LOCK_KEY = "distributed_id_generator_lock_key";
        RLock lock = redissonClient.getLock(LOCK_KEY);
        lock.lock();
        try {
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            String DISTRIBUTED_ID_GENERATOR_KEY = "distributed_id_generator_config";
            long incremented = Optional.ofNullable(instance.opsForValue().increment(DISTRIBUTED_ID_GENERATOR_KEY)).orElse(0L);
            int NODE_MAX = 32;
            if (incremented > NODE_MAX) {
                incremented = 0;
                instance.opsForValue().set(DISTRIBUTED_ID_GENERATOR_KEY, "0");
            }
            DISTRIBUTED_ID_GENERATOR = new DistributedIdGenerator(incremented);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 生成订单全局唯一ID
     * @param userId
     * @return
     */
    public static String generatorId(long userId) {
        return DISTRIBUTED_ID_GENERATOR.generateId() + String.valueOf(userId % 1000000);
    }
}
