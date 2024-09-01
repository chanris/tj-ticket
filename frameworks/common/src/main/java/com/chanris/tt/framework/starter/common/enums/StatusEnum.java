package com.chanris.tt.framework.starter.common.enums;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 状态枚举
 */
public enum StatusEnum {
    SUCCESS(0),
    FAIL(1);

    private final Integer statusCode;

    StatusEnum(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer code() {
        return this.statusCode;
    }

    public String strCode() {
        return String.valueOf(this.statusCode);
    }

    @Override
    public String toString() {
        return strCode();
    }
}
