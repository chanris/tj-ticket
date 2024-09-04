package com.chanris.tt.framework.starter.cache.core;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description
 */
@FunctionalInterface
public interface CacheLoader<T> {
    /**
     * 加载缓存
     */
    T load();
}
