package com.chanris.tt.framework.starter.idempotent.toolkit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 日志工具类
 * todo 24/9/1
 */
public class LogUtil {
    /**
     * 获取 Logger
     */
    public static Logger getLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return LoggerFactory.getLogger(methodSignature.getDeclaringType());
    }
}
