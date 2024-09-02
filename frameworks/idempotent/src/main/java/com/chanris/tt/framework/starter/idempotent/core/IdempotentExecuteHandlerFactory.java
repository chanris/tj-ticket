package com.chanris.tt.framework.starter.idempotent.core;

import com.chanris.tt.framework.starter.bases.ApplicationContextHolder;
import com.chanris.tt.framework.starter.idempotent.core.param.IdempotentParamService;
import com.chanris.tt.framework.starter.idempotent.core.token.IdempotentTokenService;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentSceneEnum;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentTypeEnum;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 幂等执行处理器工厂
 */
public class IdempotentExecuteHandlerFactory {

    public static IdempotentExecuteHandler getInstance(IdempotentSceneEnum scene, IdempotentTypeEnum type) {
        IdempotentExecuteHandler result = null;
        switch (scene) {
            case RESTAPI -> { // 不需要break
                switch (type) {
                    case PARAM -> result = ApplicationContextHolder.getBean(IdempotentParamService.class);
                    case TOKEN -> result = ApplicationContextHolder.getBean(IdempotentTokenService.class);
                }
            }
            case MQ-> {

            }
            default -> {
            }
        }
    }
}
