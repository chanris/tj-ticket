package com.chanris.tt.framework.starter.log.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson2.JSON;
import com.chanris.tt.framework.starter.log.annotation.ILog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 日志切面
 * 作用：
 * 1.代码解耦：将日志记录逻辑从业务代码中分离出来，提高业务代码的可读性
 * 2.日志打印：记录请求参数、响应参数、执行时间、请求路径、请求方式等，便于后续分析
 * 3.减少代码重复：通过切面，减少重复的日志记录代码，提高代码的复用性
 */
@Aspect
public class ILogPrintAspect {
    /**
     * 打印类或方法上的 {@link ILog}
     */
    @Around("@within(com.chanris.tt.framework.starter.log.annotation.ILog) || @annotation(com.chanris.tt.framework.starter.log.annotation.ILog)")
    public Object printMLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = SystemClock.now();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger log = LoggerFactory.getLogger(methodSignature.getDeclaringType());
        String beginTime = DateUtil.now();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
            ILog logAnnotation = Optional.ofNullable(targetMethod.getAnnotation(ILog.class)).orElse(joinPoint.getTarget().getClass().getAnnotation(ILog.class));
            if (logAnnotation != null) {
                ILogPrintDTO logPrint = new ILogPrintDTO();
                logPrint.setBeginTime(beginTime);
                // 获得方法的参数
                if (logAnnotation.input()) {
                    logPrint.setInputParams(buildInput(joinPoint));
                }
                // 获得方法执行结果
                if (logAnnotation.output()) {
                    logPrint.setOutputParams(result);
                }
                String methodType = "", requestURI = "";
                try {
                    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    assert servletRequestAttributes != null;
                    methodType = servletRequestAttributes.getRequest().getMethod();
                    requestURI = servletRequestAttributes.getRequest().getRequestURI();
                } catch (Exception ignored) {
                }
                log.info("[{}] {}, executeTime: {}ms, info: {}", methodType, requestURI, SystemClock.now() - startTime, JSON.toJSONString(logPrint));
            }
        }
        return result;
    }

    private Object[] buildInput(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object[] printArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if ((args[i] instanceof HttpServletRequest) || args[i] instanceof HttpServletResponse) {
                continue;
            }
            if (args[i] instanceof byte[]) {
                printArgs[i] = "byte array";
            } else if (args[i] instanceof MultipartFile) {
                printArgs[i] = "file";
            } else {
                printArgs[i] = args[i];
            }
        }
        return printArgs;
    }
}
