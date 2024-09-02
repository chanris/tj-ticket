package com.chanris.tt.framework.starter.log.config;

import com.chanris.tt.framework.starter.log.core.ILogPrintAspect;
import org.springframework.context.annotation.Bean;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 日志自动装配
 */
public class LogAutoConfiguration {
    /**
     * {@link ILog} 日志打印 AOP 切面
     */
    @Bean
    public ILogPrintAspect iLogPrintAspect() {
        return new ILogPrintAspect();
    }
}
