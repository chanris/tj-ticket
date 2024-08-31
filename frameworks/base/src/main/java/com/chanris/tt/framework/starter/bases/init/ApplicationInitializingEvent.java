package com.chanris.tt.framework.starter.bases.init;

import org.springframework.context.ApplicationEvent;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 应用初始化事件
 *
 * 规约事件，通过此事件可以查看业务系统所有初始化行为
 */
public class ApplicationInitializingEvent extends ApplicationEvent {

    // 在 Spring 的 ApplicationEvent 类中，source 是一个非常重要的参数，
    // 它表示事件的来源。它通常是触发事件的对象或者与事件相关的某个对象。
    public ApplicationInitializingEvent(Object source) {
        super(source);
    }
}
