package com.chanris.tt.biz.payservice.convert;

import com.chanris.tt.biz.payservice.common.enums.PayChannelEnum;
import com.chanris.tt.biz.payservice.dto.RefundCommand;
import com.chanris.tt.biz.payservice.dto.base.AliRefundRequest;
import com.chanris.tt.biz.payservice.dto.base.RefundRequest;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;

import java.util.Objects;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 退款请求入参转换器
 */
public final class RefundRequestConvert {
    /**
     * {@link RefundCommand} to {@link RefundRequest}
     *
     * @param refundCommand 退款请求参数
     * @return {@link RefundRequest}
     */
    public static RefundRequest command2RefundRequest(RefundCommand refundCommand) {
        RefundRequest refundRequest = null;
        if (Objects.equals(refundCommand.getChannel(), PayChannelEnum.ALI_PAY.getCode())) {
            refundRequest = BeanUtil.convert(refundCommand, AliRefundRequest.class);
        }
        return refundRequest;
    }
}
