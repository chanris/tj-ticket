package com.chanris.tt.biz.aggregationservice;

import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/4
 * @description
 */
@EnableDynamicThreadPool
@SpringBootApplication
public class AggregationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }
}
