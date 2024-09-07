package com.chanris.tt.biz.orderservice.mq.produce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 消息发送事件基本扩充属性实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseSendExtendDTO {

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 主题
     */
    private String topic;

    /**
     * 标签
     */
    private String tag;

    /**
     * 业务标识
     */
    private String keys;

    /**
     * 发送消息超时时间
     */
    private Long sentTimeout;

    /**
     * 延迟消息
     */
    private Integer delayLevel;
}
