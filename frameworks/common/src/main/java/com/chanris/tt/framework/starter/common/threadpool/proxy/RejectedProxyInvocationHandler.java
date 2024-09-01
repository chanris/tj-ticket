package com.chanris.tt.framework.starter.common.threadpool.proxy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description  线程池拒绝策略代理执行器
 */
@Slf4j
@AllArgsConstructor
public class RejectedProxyInvocationHandler implements InvocationHandler {

    // 目标对象
    private final Object target;
    // 拒绝数量
    private final AtomicLong rejectCount;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        rejectCount.incrementAndGet();
        try {
            log.error("线程池执行拒绝策略，此处模拟报警...");
            return method.invoke(target, args);
        }catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }
}
