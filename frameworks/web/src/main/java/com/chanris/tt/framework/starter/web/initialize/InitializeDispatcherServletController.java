package com.chanris.tt.framework.starter.web.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.chanris.tt.framework.starter.web.config.WebAutoConfiguration.INITIALIZE_PATH;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 初始化 {@link org.springframework.web.servlet.DispatcherServlet}
 */
@Slf4j(topic = "Initialize DispatcherServlet")
@RestController
public class InitializeDispatcherServletController {
    @GetMapping(INITIALIZE_PATH)
    public void initializeDispatcherServlet() {
        log.info("Initialized the dispatcherServlet to improve the first response time of the interface...");
    }
}
