package com.chanris.tt.biz.payservice.handler.base;

import com.chanris.tt.biz.payservice.dto.base.RefundRequest;
import com.chanris.tt.biz.payservice.dto.base.RefundResponse;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 抽象退款组件
 */
public abstract class AbstractRefundHandler {

    /**
     * 支付退款接口
     *
     * @param payRequest 退款请求参数
     * @return 退款响应参数
     */
    public abstract RefundResponse refund(RefundRequest payRequest);
}
