package com.chanris.tt.framework.starter.common.enums;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 删除标记枚举
 */
public enum DelEnum {
    /**
     * 正常状态
     */
    NORMAL(0),
    /**
     * 删除状态
     */
    DELETE(1);

    private final Integer statusCode;

    DelEnum(Integer statusCode) {
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
