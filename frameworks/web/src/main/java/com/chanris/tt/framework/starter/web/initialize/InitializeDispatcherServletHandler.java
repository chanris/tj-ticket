package com.chanris.tt.framework.starter.web.initialize;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

import static com.chanris.tt.framework.starter.web.config.WebAutoConfiguration.INITIALIZE_PATH;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 通过 {@link InitializeDispatcherServletController} 初始化 {@link DispatcherServlet}
 */
@RequiredArgsConstructor
public class InitializeDispatcherServletHandler implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final ConfigurableEnvironment configurableEnvironment;

    @Override
    public void run(String... args) throws Exception {
        String url = String.format("http://127.0.0.1:%s%s",
                configurableEnvironment.getProperty("server.port", "8080") +
                configurableEnvironment.getProperty("servlet.servlet.context-path", ""),
                INITIALIZE_PATH);
        try {
            restTemplate.execute(url, HttpMethod.GET, null, null);
        } catch (Throwable ignored) {
        }
    }
}
