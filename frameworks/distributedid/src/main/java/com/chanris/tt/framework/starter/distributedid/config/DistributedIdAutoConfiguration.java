package com.chanris.tt.framework.starter.distributedid.config;

import com.chanris.tt.framework.starter.bases.ApplicationContextHolder;
import com.chanris.tt.framework.starter.distributedid.core.snowflake.LocalRedisWorkIdChoose;
import com.chanris.tt.framework.starter.distributedid.core.snowflake.RandomWorkIdChoose;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 分布式 ID 自动装配
 */
@Import(ApplicationContextHolder.class)
public class DistributedIdAutoConfiguration {
    /**
     * 本地 Redis 构建雪花 WorkId 选择器
     */
    @Bean
    @ConditionalOnProperty("spring.data.redis.host") // 如果配置文件里面有这个属性，说明项目引入redis依赖
    public LocalRedisWorkIdChoose redisWorkIdChoose() {
        return new LocalRedisWorkIdChoose();
    }

    /**
     * 随机数构建雪花 WorkId 选择器。如果项目未使用 Redis，使用该选择器
     */
    @Bean
    @ConditionalOnMissingBean(LocalRedisWorkIdChoose.class)
    public RandomWorkIdChoose randomWorkIdChoose() {
        return new RandomWorkIdChoose();
    }
}
