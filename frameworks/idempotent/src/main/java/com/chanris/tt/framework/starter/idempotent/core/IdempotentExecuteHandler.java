package com.chanris.tt.framework.starter.idempotent.core;

import org.aspectj.lang.ProceedingJoinPoint;
import com.chanris.tt.framework.starter.idempotent.annotation.Idempotent;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 幂等处理器
 */
public interface IdempotentExecuteHandler {

    /**
     * 幂等处理逻辑
     *
     * @param wrapper 幂等参数包装器
     */
    void handler(IdempotentParamWrapper wrapper);

    /**
     * 执行幂等处理逻辑
     *
     * @param joinPoint  AOP 方法处理
     * @param idempotent 幂等注解
     */
    void execute(ProceedingJoinPoint joinPoint, Idempotent idempotent);

    /**
     * 异常流程处理
     */
    default void exceptionProcessing() {

    }

    /**
     * 后置处理
     */
    default void postProcessing() {

    }
}
