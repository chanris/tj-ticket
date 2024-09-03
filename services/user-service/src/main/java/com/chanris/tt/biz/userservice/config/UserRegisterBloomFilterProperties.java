package com.chanris.tt.biz.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description
 */
@Data
@ConfigurationProperties(prefix = UserRegisterBloomFilterProperties.PREFIX)
public class UserRegisterBloomFilterProperties {
    public static final String PREFIX = "framework.cache.redis.bloom-filter.user-register";
    /**
     * 用户注册布隆过滤器实例名称
     */
    private String name = "user_register_cache_penetration_bloom_filter";

    /**
     * 每个元素的预期插入量
     */
    private Long expectedInsertions = 64L;

    /**
     * 预期错误概率
     */
    private Double falseProbability = 0.03D;
}
