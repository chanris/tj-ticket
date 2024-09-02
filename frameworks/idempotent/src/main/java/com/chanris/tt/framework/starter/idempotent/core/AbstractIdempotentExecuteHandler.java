package com.chanris.tt.framework.starter.idempotent.core;

import com.chanris.tt.framework.starter.idempotent.annotation.Idempotent;
import com.chanris.tt.framework.starter.idempotent.core.param.IdempotentParamExecuteHandler;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 抽象幂等执行处理器
 */
public abstract class AbstractIdempotentExecuteHandler implements IdempotentExecuteHandler {

    /**
     * 构建幂等验证过程中所需要的参数包装器
     *
     * @param joinPoint AOP 方法处理
     * @return 幂等参数包装器
     */
    protected abstract IdempotentParamWrapper builderWrapper(ProceedingJoinPoint joinPoint);

    /**
     * 执行幂等处理逻辑
     *
     * @param joinPoint  AOP 方法处理
     * @param idempotent 幂等注解
     */
    @Override
    public void execute(ProceedingJoinPoint joinPoint, Idempotent idempotent) {
        // 模版方法模式： 构建幂等参数包装器
        IdempotentParamWrapper idempotentParamWrapper = builderWrapper(joinPoint).setIdempotent(idempotent);
        handler(idempotentParamWrapper);
    }
}
