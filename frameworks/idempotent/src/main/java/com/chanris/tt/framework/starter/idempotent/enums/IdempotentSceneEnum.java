package com.chanris.tt.framework.starter.idempotent.enums;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 幂等验证场景枚举
 */
public enum IdempotentSceneEnum {
    /**
     * 基于 RestAPI 场景验证
     */
    RESTAPI,

    /**
     * 基于 MQ 场景验证
     */
    MQ
}
