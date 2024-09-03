package com.chanris.tt.biz.userservice.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 布隆过滤器配置
 * todo 24/9/2
 */
@Configuration
@EnableConfigurationProperties(UserRegisterBloomFilterProperties.class)
public class RBloomFilterConfiguration {

    /**
     * 防止用户注册缓存穿透布隆过滤器
     */
    @Bean
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient, UserRegisterBloomFilterProperties userRegisterBloomFilterProperties) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter(userRegisterBloomFilterProperties.getName());
        cachePenetrationBloomFilter.tryInit(userRegisterBloomFilterProperties.getExpectedInsertions(), userRegisterBloomFilterProperties.getFalseProbability());
        return cachePenetrationBloomFilter;
    }
}
