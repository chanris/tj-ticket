package com.chanris.tt.biz.userservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户注册错误码枚举
 */
@AllArgsConstructor
public enum VerifyStatusEnum {
    /**
     * 未审核
     */
    UNREVIEWED(0),

    /**
     * 已审核
     */
    REVIEWED(1);

    @Getter
    private final int code;
}
