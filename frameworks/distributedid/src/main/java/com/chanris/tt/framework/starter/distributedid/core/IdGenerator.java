package com.chanris.tt.framework.starter.distributedid.core;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description ID 生成器
 */
public interface IdGenerator {
    /**
     * 下一个 ID
     */
    default long nextId() {
        return 0L;
    }
    /**
     * 下一个 ID 字符串
     */
    default String nextIdStr() {
        return "";
    }
}
