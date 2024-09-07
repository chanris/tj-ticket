package com.chanris.tt.biz.orderservice.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/4
 * @description
 */
@Getter
@RequiredArgsConstructor
public enum RefundTypeEnum {
    /**
     * 部分退款
     */
    PARTIAL_REFUND(11, 0, "PARTIAL_REFUND", "部分退款"),

    /**
     * 全部退款
     */
    FULL_REFUND(12, 1, "FULL_REFUND", "全部退款");

    private final Integer code;

    private final Integer type;

    private final String name;

    private final String value;
}
