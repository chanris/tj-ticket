package com.chanris.tt.framework.starter.idempotent.config;

import com.chanris.tt.framework.starter.cache.config.RedisDistributedProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 幂等属性配置
 */
@Data
@ConfigurationProperties(prefix = IdempotentProperties.PREFIX)
public class IdempotentProperties {
    public static final String PREFIX = "framework.idempotent.token";

    /**
     * Token 幂等 Key 前缀
     */
    private String prefix;

    /**
     * Token 申请后过期时间
     * 单位默认毫秒
     * 随着分布式缓存过期时间单位 {@link RedisDistributedProperties#valueTimeUnit} 而变化
     */
    private Long timeout;
}
