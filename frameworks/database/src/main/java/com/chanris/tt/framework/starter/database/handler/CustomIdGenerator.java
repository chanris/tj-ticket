package com.chanris.tt.framework.starter.database.handler;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.chanris.tt.framework.starter.distributedid.toolkit.SnowflakeIdUtil;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 自定义雪花算法生成器
 */
public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return SnowflakeIdUtil.nextId();
    }
}
