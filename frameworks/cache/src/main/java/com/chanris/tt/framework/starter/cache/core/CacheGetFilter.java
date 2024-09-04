package com.chanris.tt.framework.starter.cache.core;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 缓存过滤
 */
@FunctionalInterface
public interface CacheGetFilter<T> {
    /**
     * 缓存过滤
     */
    boolean filter(T param);
}
