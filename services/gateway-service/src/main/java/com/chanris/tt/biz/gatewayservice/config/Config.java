package com.chanris.tt.biz.gatewayservice.config;

import lombok.Data;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 过滤器配置
 */
@Data
public class Config {
    /**
     * 黑名单前置路径
     */
    private List<String> blackPathPre;
}
