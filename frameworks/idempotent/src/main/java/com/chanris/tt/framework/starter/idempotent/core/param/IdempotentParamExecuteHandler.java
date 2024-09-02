package com.chanris.tt.framework.starter.idempotent.core.param;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.chanris.tt.framework.starter.convention.exception.ClientException;
import com.chanris.tt.framework.starter.idempotent.core.AbstractIdempotentExecuteHandler;
import com.chanris.tt.framework.starter.idempotent.core.IdempotentContext;
import com.chanris.tt.framework.starter.idempotent.core.IdempotentParamWrapper;
import com.chanris.tt.framework.starter.user.core.UserContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 基于方法参数验证请求幂等性
 * 做法：
 * 1.生成幂等性键
 * 2.存储幂等性键
 * 3.处理请求并存储结果
 */
@RequiredArgsConstructor
public class IdempotentParamExecuteHandler extends AbstractIdempotentExecuteHandler implements IdempotentParamService {

    private final RedissonClient redissonClient;

    private final static String LOCK = "lock:param:restAPI";

    @Override
    protected IdempotentParamWrapper builderWrapper(ProceedingJoinPoint joinPoint) {
        String lockKey = String.format("idempotent:path:%s:currentUserId:%s:md5:%s", getServletPath(), getCurrentUserId(), calcArgsMD5(joinPoint));
        return IdempotentParamWrapper.builder().lockKey(lockKey).joinPoint(joinPoint).build();
    }

    /**
     * @return 获取当前线程上下文 ServletPath
     */
    private String getServletPath() {
        ServletRequestAttributes src = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return src.getRequest().getServletPath();
    }

    /**
     * 获取操作用户 ID
     */
    private String getCurrentUserId() {
        String userId = UserContext.getUserId();
        if (StrUtil.isBlank(userId)) {
            throw new ClientException("用户ID获取失败，请登录");
        }
        return userId;
    }

    /**
     * 请求参数做md5处理
     *
     * @param joinPoint 请求切面
     * @return 参数的md5字符串
     */
    private String calcArgsMD5(ProceedingJoinPoint joinPoint) {
        return DigestUtil.md5Hex(JSON.toJSONBytes(joinPoint.getArgs()));
    }

    /**
     * @param wrapper 幂等参数包装器
     */
    @Override
    public void handler(IdempotentParamWrapper wrapper) {
        String lockKey = wrapper.getLockKey();
        RLock lock = redissonClient.getLock(lockKey);
        if (!lock.tryLock()) {
            throw new ClientException(wrapper.getIdempotent().message());
        }
        IdempotentContext.put(LOCK, lock);
    }

    @Override
    public void postProcessing() {
        RLock lock = null;
        try {
            lock = (RLock) IdempotentContext.getKey(LOCK);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }
}
