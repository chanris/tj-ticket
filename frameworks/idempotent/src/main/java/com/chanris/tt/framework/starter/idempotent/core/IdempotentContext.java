package com.chanris.tt.framework.starter.idempotent.core;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 幂等上下文
 */
public class IdempotentContext {
    private static final ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<>();

    public static Map<String, Object> get() {
        return CONTEXT.get();
    }

    public static Object getKey(String key) {
        Map<String, Object> context = get();
        if (CollUtil.isNotEmpty(context)) {
            return context.get(key);
        }
        return null;
    }

    public static String getString(String key) {
        Object actual = getKey(key);
        if (actual != null) {
            return actual.toString();
        }
        return null;
    }

    public static void put(String key, Object val) {
        Map<String, Object> context = get();
        if (CollUtil.isEmpty(context)) {
            context = Maps.newHashMap();
        }
        context.put(key, val);
        putContext(context);
    }

    public static void putContext(Map<String, Object> context) {
        Map<String, Object> threadContext = CONTEXT.get();
        if (CollUtil.isNotEmpty(threadContext)) {
            threadContext.putAll(context);
            return;
        }
        CONTEXT.set(context);
    }

    public static void clean() {
        CONTEXT.remove();
    }
}
