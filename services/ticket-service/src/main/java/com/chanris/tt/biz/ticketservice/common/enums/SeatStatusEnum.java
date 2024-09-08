package com.chanris.tt.biz.ticketservice.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@Getter
@RequiredArgsConstructor
public enum SeatStatusEnum {
    /**
     * 可售
     */
    AVAILABLE(0),
    /**
     * 锁定
     */
    LOCKED(1),
    /**
     * 已售
     */
    SOLD(2);

    private final Integer code;
}
