package com.chanris.tt.framework.starter.bases.safa;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description
 */
public class FastJsonSafeMode implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        /*
         * 启用fastjson2的安全模式
         * 限制反序列化的类：防止反序列化到一些不安全的类。
         * 检查输入：更严格地检查输入 JSON 数据，防止恶意构造的数据导致程序异常。
         * 限制反序列化特性：禁用一些可能带来安全风险的反序列化特性。
         */
        System.setProperty("fastjson2.parser.safeMode", "true");
    }
}
