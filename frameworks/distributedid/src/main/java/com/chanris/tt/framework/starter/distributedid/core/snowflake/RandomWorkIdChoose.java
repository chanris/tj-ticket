package com.chanris.tt.framework.starter.distributedid.core.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 使用随机数获取雪花 WorkId
 */
@Slf4j
public class RandomWorkIdChoose extends AbstractWorkIdChooseTemplate implements InitializingBean {
    @Override
    protected WorkIdWrapper chooseWorkId() {
        int start = 0, end = 31;
        return new WorkIdWrapper(getRandom(start, end), getRandom(start, end));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chooseAndInit();
    }

    private static long getRandom(int start, int end) {
        long random = (long) (Math.random() * (end - start + 1) + start);
        return random;
    }
}
