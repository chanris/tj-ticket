package com.chanris.tt.biz.orderservice;

import cn.crane4j.spring.boot.annotation.EnableCrane4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/4
 * @description
 */
@SpringBootApplication
@MapperScan("com.chanris.tt.biz.orderservice.dao.mapper")
@EnableFeignClients("com.chanris.tt.biz.orderservice.remote")
@EnableCrane4j(enumPackages = "com.chanris.tt.biz.orderservice.common.enums") // todo 24/9/5
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
