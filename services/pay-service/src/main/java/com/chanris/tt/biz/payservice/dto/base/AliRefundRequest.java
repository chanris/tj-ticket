package com.chanris.tt.biz.payservice.dto.base;

import com.chanris.tt.biz.payservice.common.enums.PayChannelEnum;
import com.chanris.tt.biz.payservice.common.enums.PayTradeTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 支付宝退款请求入参
 */
@Data
@Accessors(chain = true)
public final class AliRefundRequest extends AbstractRefundRequest {

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 交易凭证号
     */
    private String tradeNo;

    @Override
    public AliRefundRequest getAliRefundRequest() {
        return this;
    }

    @Override
    public String buildMark() {
        String mark = PayChannelEnum.ALI_PAY.name();
        if (getTradeType() != null) {
            mark = PayChannelEnum.ALI_PAY.name() + "_" + PayTradeTypeEnum.findNameByCode(getTradeType()) + "_" + TradeStatusEnum.TRADE_CLOSED.tradeCode();
        }
        return mark;
    }
}
