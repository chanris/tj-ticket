package com.chanris.tt.biz.payservice.convert;

import com.chanris.tt.biz.payservice.common.enums.PayChannelEnum;
import com.chanris.tt.biz.payservice.dto.PayCommand;
import com.chanris.tt.biz.payservice.dto.base.AliPayRequest;
import com.chanris.tt.biz.payservice.dto.base.PayRequest;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;

import java.util.Objects;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 支付请求入参转换器
 */
public final class PayRequestConvert {

    /**
     * {@link PayCommand} to {@link PayRequest}
     *
     * @param payCommand 支付请求参数
     * @return {@link PayRequest}
     */
    public static PayRequest command2PayRequest(PayCommand payCommand) {
        PayRequest payRequest = null;
        if (Objects.equals(payCommand.getChannel(), PayChannelEnum.ALI_PAY.getCode())) {
            payRequest = BeanUtil.convert(payCommand, AliPayRequest.class);
        }
        return payRequest;
    }
}
