package com.chanris.tt.biz.payservice.handler.base;

import com.chanris.tt.biz.payservice.dto.base.PayCallbackRequest;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 抽象支付回调组件
 */
public abstract class AbstractPayCallbackHandler {

    /**
     * 支付回调抽象接口
     *
     * @param payCallbackRequest 支付回调请求参数
     */
    public abstract void callback(PayCallbackRequest payCallbackRequest);
}
