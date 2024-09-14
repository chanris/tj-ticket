package com.chanris.tt.biz.payservice.convert;

import com.chanris.tt.biz.payservice.common.enums.PayChannelEnum;
import com.chanris.tt.biz.payservice.dto.PayCallbackCommand;
import com.chanris.tt.biz.payservice.dto.base.AliPayCallbackRequest;
import com.chanris.tt.biz.payservice.dto.base.PayCallbackRequest;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;

import java.util.Objects;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description
 */
public final class PayCallbackRequestConvert {
    /**
     * {@link PayCallbackCommand} to {@link PayCallbackRequest}
     *
     * @param payCallbackCommand 支付回调请求参数
     * @return {@link PayCallbackRequest}
     */
    public static PayCallbackRequest command2PayCallbackRequest(PayCallbackCommand payCallbackCommand) {
        PayCallbackRequest payCallbackRequest = null;
        if (Objects.equals(payCallbackCommand.getChannel(), PayChannelEnum.ALI_PAY.getCode())) {
            payCallbackRequest = BeanUtil.convert(payCallbackCommand, AliPayCallbackRequest.class);
            ((AliPayCallbackRequest) payCallbackRequest).setOrderRequestId(payCallbackCommand.getOrderRequestId());
        }
        return payCallbackRequest;
    }
}
