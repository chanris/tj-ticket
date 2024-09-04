package com.chanris.tt.framework.starter.cache;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 缓存接口
 */
public interface Cache {

    /**
     * 获取缓存
     */
    <T> T get(@NotBlank String key, Class<T> clazz);

    /**
     * 放入缓存
     */
    void put(@NotBlank String key, Object value);

    /**
     * 如果keys 全部不存在，则新增，返回true，反之 false
     */
    Boolean putIfAllAbsent(@NotNull Collection<String> keys);

    /**
     * 删除缓存
     */
    Boolean delete(@NotBlank String key);

    /**
     * 删除缓存
     */
    Long delete(@NotNull Collection<String> keys);

    /**
     * 判断 key 是否存在
     */
    Boolean hasKey(@NotBlank String key);

    /**
     * 获取缓存组件实例
     */
    Object getInstance();
}
