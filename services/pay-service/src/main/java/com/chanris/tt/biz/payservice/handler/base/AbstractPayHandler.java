package com.chanris.tt.biz.payservice.handler.base;

import com.chanris.tt.biz.payservice.dto.base.PayRequest;
import com.chanris.tt.biz.payservice.dto.base.PayResponse;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 抽象支付组件
 */
public abstract class AbstractPayHandler {

    /**
     * 支付抽象接口
     *
     * @param payRequest 支付请求参数
     * @return 支付响应参数
     */
    public abstract PayResponse pay(PayRequest payRequest);
}
