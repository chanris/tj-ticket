package com.chanris.tt.framework.starter.designpattern.strategy;

import com.chanris.tt.framework.starter.bases.init.ApplicationInitializingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 策略选择器
 */
public class AbstractStrategyChoose implements ApplicationListener<ApplicationInitializingEvent> {

    /**
     * 执行策略合集
     */
    private final Map<String, AbstractExecuteStrategy> abstractExecuteStrategyMap = new HashMap<>();


    public AbstractExecuteStrategy choose(String mark, Boolean predicateFlag) {
        if (predicateFlag != null && predicateFlag) {
            return abstractExecuteStrategyMap.values().stream()
                    .filter(each -> StringUtils.hasText(each.patternMatchMark()))
                    .filter(each -> Pattern.compile(each.patternMatchMark()).matcher(mark).matches())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("策略未定义"));
        }
        return Optional.ofNullable(abstractExecuteStrategyMap.get(mark))
                .orElseThrow(() ->)
    }


    @Override
    public void onApplicationEvent(ApplicationInitializingEvent event) {

    }
}
