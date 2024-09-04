package com.chanris.tt.framework.starter.cache.core;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 缓存查询为空
 */
@FunctionalInterface
public interface CacheGetIfAbsent<T> {
    /**
     * 如果缓存查询为空，执行逻辑
     */
    void execute(T param);
}
