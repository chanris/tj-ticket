package com.chanris.tt.framework.starter.designpattern.builder;

import java.io.Serializable;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description Builder 模式抽象接口
 */
public interface Builder<T> extends Serializable {
    /**
     * 构建方法
     *
     * @return 构建后的对象
     */
    T build();
}
