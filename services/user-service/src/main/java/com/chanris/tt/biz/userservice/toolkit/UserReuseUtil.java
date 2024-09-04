package com.chanris.tt.biz.userservice.toolkit;

import static com.chanris.tt.biz.userservice.common.constant.TTConstant.USER_REGISTER_REUSE_SHARDING_COUNT;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/3
 * @description 用户名可复用工具类
 */
public final class UserReuseUtil {
    public static int hashShardingIdx(String username) {
        return Math.abs(username.hashCode() % USER_REGISTER_REUSE_SHARDING_COUNT);
    }
}
