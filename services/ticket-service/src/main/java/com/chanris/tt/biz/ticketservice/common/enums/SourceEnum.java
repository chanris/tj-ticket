package com.chanris.tt.biz.ticketservice.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 购票来源
 */
@Getter
@RequiredArgsConstructor
public enum SourceEnum {
    /**
     * 互联网购票
     */
    INTERNEt(0),
    /**
     * 线下窗口购票
     */
    OFFLINE(1);
    private final Integer code;
}
