package com.chanris.tt.biz.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/4
 * @description
 */
@SpringBootApplication
@MapperScan("com.chanris.tt.biz.userservice.dao.mapper")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
