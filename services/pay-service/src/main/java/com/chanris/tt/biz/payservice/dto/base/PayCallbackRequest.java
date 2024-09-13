package com.chanris.tt.biz.payservice.dto.base;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 支付回调请求入参
 */
public interface PayCallbackRequest {

    /**
     * 获取阿里支付回调入参
     */
    AliPayCallbackRequest getAliPayCallBackRequest();

    /**
     * 构建查找支付回调策略实现类标识
     */
    String buildMark();
}

