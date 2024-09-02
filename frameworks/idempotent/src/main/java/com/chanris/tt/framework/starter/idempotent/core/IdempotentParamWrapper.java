package com.chanris.tt.framework.starter.idempotent.core;

import com.chanris.tt.framework.starter.idempotent.annotation.Idempotent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.aspectj.lang.ProceedingJoinPoint;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentTypeEnum;
/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class IdempotentParamWrapper {

    /**
     * 幂等注解
     */
    private Idempotent idempotent;

    /**
     * AOP 处理连接点
     */
    private ProceedingJoinPoint joinPoint;

    /**
     * 锁标识，{@link IdempotentTypeEnum#PARAM}
     */
    private String lockKey;
}
