package com.chanris.tt.framework.starter.cache.toolkit;

import com.alibaba.fastjson2.util.ParameterizedTypeImpl;

import java.lang.reflect.Type;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description Fastjson2 工具类
 * //todo 24/9/1 看不懂
 */
public class FastJson2Util {

    /**
     * 构建类型
     *
     * @param types
     * @return
     */
    public static Type buildType(Type... types) {
        ParameterizedTypeImpl beforeType = null;
        if (types != null && types.length > 0) {
            if (types.length == 1) {
                return new ParameterizedTypeImpl(new Type[]{null}, null, types[0]);
            }
            for (int i = types.length - 1; i > 0; i--) {
                beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null ? types[i] : beforeType}, null, types[i - 1]);
            }
            return beforeType;
        }
        return beforeType;
    }
}
