package com.chanris.tt.biz.gatewayservice.filter;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.chanris.tt.biz.gatewayservice.config.Config;
import com.chanris.tt.biz.gatewayservice.toolkit.JWTUtil;
import com.chanris.tt.biz.gatewayservice.toolkit.UserInfoDTO;
import com.chanris.tt.framework.starter.bases.constant.UserConstant;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description SpringCloud Gateway Token 拦截器
 */
@Component // 网关过滤器需要加入ioc容器
public class TokenValidateGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> { // AbstractGatewayFilterFactory 提供了创建自定义过滤器的基础结构，简化了自定义过滤器的开发过程。

    public TokenValidateGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 注销用户时需要传递 Token
     */
    public static final String DELETION_PATH = "/api/user-service/deletion";

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 获取请求
            ServerHttpRequest request = exchange.getRequest();
            // 获取请求uri
            String requestPath = request.getPath().toString();
            // 判断请求路径是否在黑名单列表中
            if (isPathInBlackPreList(requestPath, config.getBlackPathPre())) {
                // 若在，需要验证token信息
                String token = request.getHeaders().getFirst("Authorization");
                // TODO 需要验证 Token 是否有效，有可能用户注销了账户，但是 Token 有效期还未过
                UserInfoDTO userInfo = JWTUtil.parseJwtToken(token);
                if (!validateToken(userInfo)) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    // 验证失败，返回response
                    return response.setComplete();
                }

                // 添加用户信息到请求头
                ServerHttpRequest.Builder builder = exchange.getRequest().mutate().headers(httpHeaders -> {
                    httpHeaders.set(UserConstant.USER_ID_KEY, userInfo.getUserId());
                    httpHeaders.set(UserConstant.USER_NAME_KEY, userInfo.getUsername());
                    httpHeaders.set(UserConstant.REAL_NAME_KEY, URLEncoder.encode(userInfo.getRealName(), StandardCharsets.UTF_8));
                    if (Objects.equals(requestPath, DELETION_PATH)) {
                        httpHeaders.set(UserConstant.USER_TOKEN_KEY, token);
                    }
                });
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
            // 验证成功，继续其他过滤器
            return chain.filter(exchange);
        };
    }

    private boolean isPathInBlackPreList(String requestPath, List<String> blackPathPre) {
        if (CollectionUtils.isEmpty(blackPathPre)) {
            return false;
        }
        return blackPathPre.stream().anyMatch(requestPath::startsWith);
    }

    private boolean validateToken(UserInfoDTO userInfo) {
        return userInfo != null;
    }
}
