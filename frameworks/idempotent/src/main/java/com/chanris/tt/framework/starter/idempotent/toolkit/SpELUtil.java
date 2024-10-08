package com.chanris.tt.framework.starter.idempotent.toolkit;

import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description SpEL 表达式解析工具
 *
 * //todo 24/9/1
 */
public class SpELUtil {
    /**
     * 校验并返回实际使用的 spEL 表达式
     *
     * @param spEl spEL 表达式
     * @return 实际使用的 spEL 表达式
     */
    public static Object parseKey(String spEl, Method method, Object[] contextObj) {
        ArrayList<String> spELFlag = Lists.newArrayList("#", "T(");
        Optional<String> optional = spELFlag.stream().filter(spEl::contains).findFirst();
        if (optional.isPresent()) {
            // 如果spEl中存在#或者 T( 则进行解析
            return parse(spEl, method, contextObj);
        }
        return spEl;
    }

    /**
     * 转换参数为字符串
     *
     * @param spEl       spEl 表达式
     * @param contextObj 上下文对象（方法的实参列表）
     * @return 解析的字符串值
     */
    public static Object parse(String spEl, Method method, Object[] contextObj) {
        // 此时确定spEl包含# 或者 T() 格式，需要参数填充
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(spEl);
        // 获取方法的参数名称列表
        String[] params = discoverer.getParameterNames(method);
        // 获取方法的参数列表上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (ArrayUtil.isNotEmpty(params)) {
            for (int len = 0; len < params.length; len++) {
                context.setVariable(params[len], contextObj[len]);
            }
        }
        // spEl设置参数列表上下文，并获得返回值
        return exp.getValue(context);
    }
}
