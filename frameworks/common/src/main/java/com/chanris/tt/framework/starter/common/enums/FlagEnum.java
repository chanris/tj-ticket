package com.chanris.tt.framework.starter.common.enums;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 标识枚举，非 {@code true} 即 {@code false}
 */
public enum FlagEnum {
    FALSE(0),
    TRUE(1);

    private final Integer flag;

    FlagEnum(Integer flag) {
        this.flag = flag;
    }

    public Integer code() {
        return this.flag;
    }

    public String strCode() {
        return String.valueOf(this.flag);
    }

    @Override
    public String toString() {
        return strCode();
    }
}
