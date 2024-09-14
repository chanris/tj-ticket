package com.chanris.tt.biz.aggregationservice;

import cn.crane4j.spring.boot.annotation.EnableCrane4j;
import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/4
 * @description
 */
@EnableDynamicThreadPool
@SpringBootApplication(scanBasePackages = {
        "com.chanris.tt.biz.userservice",
        "com.chanris.tt.biz.ticketservice",
        "com.chanris.tt.biz.orderservice",
        "com.chanris.tt.biz.payservice"
})
@EnableRetry
@MapperScan(value = {
        "com.chanris.tt.biz.userservice.dao.mapper",
        "com.chanris.tt.biz.ticketservice.dao.mapper",
        "com.chanris.tt.biz.orderservice.dao.mapper",
        "com.chanris.tt.biz.payservice.dao.mapper"
})
@EnableFeignClients(value = {
        "com.chanris.tt.biz.ticketservice.remote",
        "com.chanris.tt.biz.orderservice.remote"
})
@EnableCrane4j(enumPackages = "com.chanris.tt.biz.orderservice.common.enums")
public class AggregationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }
}
