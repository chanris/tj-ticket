package com.chanris.tt.framework.starter.common.toolkit;

import lombok.SneakyThrows;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 线程池工具类
 */
public class ThreadUtil {

    @SneakyThrows(value = InterruptedException.class)
    public static void sleep(long millis) {
        Thread.sleep(millis);
    }
}
