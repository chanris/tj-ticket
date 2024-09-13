package com.chanris.tt.biz.payservice.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 退款返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class RefundResponse {

    /**
     * 退款状态
     */
    private Integer status;

    /**
     * 第三方交易凭证
     */
    private String tradeNo;
}
