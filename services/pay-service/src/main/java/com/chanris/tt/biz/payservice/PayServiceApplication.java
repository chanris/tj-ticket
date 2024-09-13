package com.chanris.tt.biz.payservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 支付服务应用启动器
 */
@SpringBootApplication
@MapperScan("com.chanris.tt.biz.payservice.dao.mapper")
@EnableFeignClients("com.chanris.tt.biz.payservice.remote")
@EnableRetry
public class PayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayServiceApplication.class, args);
    }
}

