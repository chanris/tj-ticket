package com.chanris.tt.framework.starter.designpattern.chain;

import com.chanris.tt.framework.starter.bases.ApplicationContextHolder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 抽象责任链上下文
 */
public final class AbstractChainContext<T> implements CommandLineRunner { // 执行启动后的初始化操作

    // 责任链容器 key: 一组责任链的标识mark, value: List<AbstractChainHandler>责任链
    private final Map<String, List<AbstractChainHandler>> abstractChainHandlerContainer = new HashMap<>();

    /**
     * 责任链组件执行
     *
     * @param mark         责任链组件标识
     * @param requestParam 请求参数
     */
    public void handler(String mark, T requestParam) {
        List<AbstractChainHandler> abstractChainHandlers = abstractChainHandlerContainer.get(mark);
        // 根据 mark 获得责任链
        if (CollectionUtils.isEmpty(abstractChainHandlers)) {
            throw new RuntimeException(String.format("[%s] Chain of Responsibility ID is undefined.", mark));
        }
        // 执行责任链，如果没有抛异常则说明执行成功
        abstractChainHandlers.forEach(each -> each.handler(requestParam));
    }

    /**
     * IOC 初始化完毕后执行
     */
    @Override
    public void run(String... args) throws Exception {
        // 获得注册的责任链组件
        Map<String, AbstractChainHandler> chainFilterMap = ApplicationContextHolder.getBeansOfType(AbstractChainHandler.class);
        // 遍历责任链组件，按mark分组
        chainFilterMap.forEach((beanName, bean)-> {
            List<AbstractChainHandler> abstractChainHandlers =  abstractChainHandlerContainer.get(bean.mark());
            // 如果组件还未放入 chainHandlerContainer， 则放入
            if (CollectionUtils.isEmpty(abstractChainHandlers)) {
                abstractChainHandlers = new ArrayList<>();
            }
            abstractChainHandlers.add(bean);
            List<AbstractChainHandler> actualAbstractChainHandlers = abstractChainHandlers.stream()
                    .sorted(Comparator.comparing(Ordered::getOrder))
                    .collect(Collectors.toList());
            // 将责任链组件排序
            abstractChainHandlerContainer.put(bean.mark(), actualAbstractChainHandlers);
        });
    }
}
