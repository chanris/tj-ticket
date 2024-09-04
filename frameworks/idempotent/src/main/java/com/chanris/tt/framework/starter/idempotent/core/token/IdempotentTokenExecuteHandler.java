package com.chanris.tt.framework.starter.idempotent.core.token;

import cn.hutool.core.util.StrUtil;
import com.chanris.tt.framework.starter.cache.DistributedCache;
import com.chanris.tt.framework.starter.convention.errorcode.BaseErrorCode;
import com.chanris.tt.framework.starter.convention.exception.ClientException;
import com.chanris.tt.framework.starter.idempotent.config.IdempotentProperties;
import com.chanris.tt.framework.starter.idempotent.core.AbstractIdempotentExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.IdempotentParamWrapper;
import com.google.common.base.Strings;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.UUID;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 基于 Token 验证请求幂等性, 通常应用于 RestAPI 方法
 * 用法：
 * 1.服务端生成幂等token（通常是UUID）,将幂等Token存入Redis
 * 2.客户端获取幂等token（请求url: xxx/token）
 * 3.服务器接收请求并检查Token
 * 4.存储Token和结果
 * 5.响应请求
 */
@RequiredArgsConstructor
public class IdempotentTokenExecuteHandler extends AbstractIdempotentExecuteHandler implements IdempotentTokenService{

    private final DistributedCache distributedCache;
    private final IdempotentProperties idempotentProperties;

    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_PREFIX_KEY = "idempotent:token";
    private static final long TOKEN_EXPIRED_TIME = 6000;

    @Override
    protected IdempotentParamWrapper builderWrapper(ProceedingJoinPoint joinPoint) {
        return new IdempotentParamWrapper();
    }

    @Override
    public String createToken() {
        String token = Optional.ofNullable(Strings.emptyToNull(idempotentProperties.getPrefix())).orElse(TOKEN_PREFIX_KEY) + UUID.randomUUID();
        distributedCache.put(token, "", Optional.ofNullable(idempotentProperties.getTimeout()).orElse(TOKEN_EXPIRED_TIME));
        return token;
    }

    @Override
    public void handler(IdempotentParamWrapper wrapper) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader(TOKEN_KEY);
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(TOKEN_KEY);
            if (StrUtil.isBlank(token)) {
                throw new ClientException(BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR);
            }
        }
        Boolean tokenDelFlag = distributedCache.delete(token);
        if (!tokenDelFlag) {
            String errMsg = StrUtil.isNotBlank(wrapper.getIdempotent().message())
                    ? wrapper.getIdempotent().message()
                    : BaseErrorCode.IDEMPOTENT_TOKEN_DELETE_ERROR.message();
            throw new ClientException(errMsg, BaseErrorCode.IDEMPOTENT_TOKEN_DELETE_ERROR);
        }
    }
}
