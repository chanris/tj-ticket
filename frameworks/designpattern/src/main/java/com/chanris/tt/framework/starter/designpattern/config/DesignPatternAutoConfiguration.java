package com.chanris.tt.framework.starter.designpattern.config;

import com.chanris.tt.framework.starter.bases.config.ApplicationBaseAutoConfiguration;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainContext;
import com.chanris.tt.framework.starter.designpattern.strategy.AbstractStrategyChoose;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 设计模式自动装配
 */
@ImportAutoConfiguration(ApplicationBaseAutoConfiguration.class)
public class DesignPatternAutoConfiguration {
    @Bean
    public AbstractStrategyChoose abstractStrategyChoose() {
        return new AbstractStrategyChoose();
    }

    @Bean
    public AbstractChainContext abstractChainContext() {
        return new AbstractChainContext();
    }
}
