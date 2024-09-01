package com.chanris.tt.framework.starter.common.toolkit;

import com.chanris.tt.framework.starter.bases.ApplicationContextHolder;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 环境工具类
 */
public class EnvironmentUtil {
    private static List<String> ENVIRONMENT_LIST = new ArrayList<>();

    static {
        ENVIRONMENT_LIST.add("dev");
        ENVIRONMENT_LIST.add("test");
    }

    public static boolean isDevEnvironment() {
        ConfigurableEnvironment configurableEnvironment = ApplicationContextHolder.getBean(ConfigurableEnvironment.class);
        String propertyActive = configurableEnvironment.getProperty("spring.profiles.active", "dev");
        return ENVIRONMENT_LIST.stream().filter(each -> propertyActive.contains(each)).findFirst().isPresent();
    }

    /**
     * 判断当前是否为正式环境
     *
     * @return
     */
    public static boolean isProdEnvironment() {
        ConfigurableEnvironment configurableEnvironment = ApplicationContextHolder.getBean(ConfigurableEnvironment.class);
        String propertyActive = configurableEnvironment.getProperty("spring.profiles.active", "dev");
        return !ENVIRONMENT_LIST.stream().filter(each -> propertyActive.contains(each)).findFirst().isPresent();
    }
}
