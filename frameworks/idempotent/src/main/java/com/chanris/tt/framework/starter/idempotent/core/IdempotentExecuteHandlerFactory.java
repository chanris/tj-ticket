package com.chanris.tt.framework.starter.idempotent.core;

import com.chanris.tt.framework.starter.bases.ApplicationContextHolder;
import com.chanris.tt.framework.starter.idempotent.core.param.IdempotentParamService;
import com.chanris.tt.framework.starter.idempotent.core.spel.IdempotentSpELByMQExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.spel.IdempotentSpELByRestAPIExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.spel.IdempotentSpELService;
import com.chanris.tt.framework.starter.idempotent.core.token.IdempotentTokenService;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentSceneEnum;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentTypeEnum;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 幂等执行处理器工厂
 */
public class IdempotentExecuteHandlerFactory {

    // 根据scene和type通过switch-case语句，再通过SpringContext.getBean从IOC容器取到对应的Handler
    // 具体来说场景为RESTAPI，根据类型有三种处理器
    // 场景为MQ，只有一种处理器，也就是SPEL
    public static IdempotentExecuteHandler getInstance(IdempotentSceneEnum scene, IdempotentTypeEnum type) {
        IdempotentExecuteHandler result = null;
        switch (scene) {
            case RESTAPI -> { // 不需要break
                switch (type) {
                    case PARAM -> result = ApplicationContextHolder.getBean(IdempotentParamService.class);
                    case TOKEN -> result = ApplicationContextHolder.getBean(IdempotentTokenService.class);
                    case SPEL -> result = ApplicationContextHolder.getBean(IdempotentSpELByRestAPIExecuteHandler.class);
                    default -> {
                    }
                }
            }
            case MQ-> result = ApplicationContextHolder.getBean(IdempotentSpELByMQExecuteHandler.class);
            default -> {
            }
        }
        return result;
    }
}
