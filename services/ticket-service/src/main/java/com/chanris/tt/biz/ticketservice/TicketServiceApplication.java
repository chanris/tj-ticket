package com.chanris.tt.biz.ticketservice;

import cn.hippo4j.core.enable.EnableDynamicThreadPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 购票服务应用启动器
 */
@EnableDynamicThreadPool
@SpringBootApplication
@MapperScan("com.chanris.tt.biz.ticketservice.dao.mapper")
@EnableFeignClients("com.chanris.tt.biz.ticketservice.remote")
public class TicketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }
}
