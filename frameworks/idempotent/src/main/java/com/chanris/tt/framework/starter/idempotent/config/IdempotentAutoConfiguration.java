package com.chanris.tt.framework.starter.idempotent.config;

import com.chanris.tt.framework.starter.cache.DistributedCache;
import com.chanris.tt.framework.starter.idempotent.core.IdempotentAspect;
import com.chanris.tt.framework.starter.idempotent.core.param.IdempotentParamExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.param.IdempotentParamService;
import com.chanris.tt.framework.starter.idempotent.core.spel.IdempotentSpELByMQExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.spel.IdempotentSpELByRestAPIExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.spel.IdempotentSpELService;
import com.chanris.tt.framework.starter.idempotent.core.token.IdempotentTokenExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.token.IdempotentTokenService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 幂等自动装配
 */
@EnableConfigurationProperties(IdempotentProperties.class)
public class IdempotentAutoConfiguration {

    /**
     * 幂等切面
     */
    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }

    /**
     * 参数方法幂等实现，基于RestAPI 场景
     * @param redissonClient
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentParamService idempotentParamExecuteHandler(RedissonClient redissonClient) {
        return new IdempotentParamExecuteHandler(redissonClient);
    }

    /**
     * 申请幂等 Token 控制器，基于 RestAPI 场景
     */
    @Bean
    public IdempotentTokenService idempotentTokenExecuteHandler(DistributedCache distributedCache,
                                                                IdempotentProperties idempotentProperties) {
        return new IdempotentTokenExecuteHandler(distributedCache, idempotentProperties);
    }

    /**
     * SpEL 方式幂等实现，基于 RestAPI 场景
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentSpELService idempotentSpELByRestAPIExecuteHandler(RedissonClient redissonClient) {
        return new IdempotentSpELByRestAPIExecuteHandler(redissonClient);
    }

    /**
     * SpEL 方式幂等实现，基于 MQ 场景
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentSpELByMQExecuteHandler idempotentSpELByMQExecuteHandler(DistributedCache distributedCache) {
        return new IdempotentSpELByMQExecuteHandler(distributedCache);
    }
}
