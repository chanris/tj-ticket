package com.chanris.tt.biz.orderservice.mq.domain;

import lombok.*;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 消息体包装器
 */
@Data
@Builder
@NoArgsConstructor(force = true) // force = true,将所有final字段初始化为 0 / null / false
@AllArgsConstructor
@RequiredArgsConstructor
public final class MessageWrapper<T> implements Serializable {
    private static final long serialVersion = 1L;

    @NonNull
    private String keys;

    @Nonnull
    private T message;

    /**
     * 唯一标识，用于客户端幂等验证
     */
    private String uuid = UUID.randomUUID().toString();

    /**
     * 消息发送时间
     */
    private Long timestamp = System.currentTimeMillis();
}
