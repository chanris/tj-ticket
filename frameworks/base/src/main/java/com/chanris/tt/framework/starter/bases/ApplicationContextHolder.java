package com.chanris.tt.framework.starter.bases;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 应用上下文信息持有者
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.CONTEXT = applicationContext;
    }

    /**
     * 通过类型获取bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }

    /**
     * 通过名称获取bean
     */
    public static Object getBean(String name) {
        return CONTEXT.getBean(name);
    }

    /**
     * 根据名称和类型获取bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return CONTEXT.getBean(name, clazz);
    }

    /**
     * 根据类型获取beans集合
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return CONTEXT.getBeansOfType(clazz);
    }

    /**
     * 在bean上查找注解
     */
    public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
        return CONTEXT.findAnnotationOnBean(beanName, annotationType);
    }

    /**
     * 获得上下文环境
     */
    public static ApplicationContext getInstance() {
        return CONTEXT;
    }
}
