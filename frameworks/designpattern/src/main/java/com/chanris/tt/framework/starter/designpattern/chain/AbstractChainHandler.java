package com.chanris.tt.framework.starter.designpattern.chain;

import org.springframework.core.Ordered;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description
 */
public interface AbstractChainHandler<T> extends Ordered {
    /**
     * 执行责任链逻辑
     * @param requestParam
     */
    void handler(T requestParam);

    /**
     * @return 责任链组件标记
     */
    String mark();
}
