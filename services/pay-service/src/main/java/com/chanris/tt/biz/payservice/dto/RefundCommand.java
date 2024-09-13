package com.chanris.tt.biz.payservice.dto;

import com.chanris.tt.biz.payservice.dto.base.AbstractRefundRequest;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 退款请求命令
 */
@Data
public final class RefundCommand extends AbstractRefundRequest {

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 交易凭证号
     */
    private String tradeNo;
}
