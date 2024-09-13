package com.chanris.tt.biz.payservice.service;

import com.chanris.tt.biz.payservice.dto.RefundReqDTO;
import com.chanris.tt.biz.payservice.dto.RefundRespDTO;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 退款接口层
 */
public interface RefundService {
    /**
     * 公共退款接口
     *
     * @param requestParam 退款请求参数
     * @return 退款返回详情
     */
    RefundRespDTO commonRefund(RefundReqDTO requestParam);
}
