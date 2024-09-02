package com.chanris.tt.framework.starter.idempotent.core.token;

import com.chanris.tt.framework.starter.idempotent.core.IdempotentExecuteHandler;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description Token 实现幂等接口
 */
public interface IdempotentTokenService extends IdempotentExecuteHandler {
    /**
     * 创建幂等验证Token
     */
   String createToken();
}
