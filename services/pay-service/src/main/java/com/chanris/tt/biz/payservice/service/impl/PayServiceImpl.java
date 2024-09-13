package com.chanris.tt.biz.payservice.service.impl;

import com.chanris.tt.biz.payservice.dto.*;
import com.chanris.tt.biz.payservice.dto.base.PayRequest;
import com.chanris.tt.biz.payservice.service.PayService;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 支付接口层实现
 */
public class PayServiceImpl implements PayService {

    @Override
    public PayRespDTO commonPay(PayRequest requestParam) {
        return null;
    }

    @Override
    public void callbackPay(PayCallbackReqDTO requestParam) {

    }

    @Override
    public PayInfoRespDTO getPayInfoByOrderSn(String orderSn) {
        return null;
    }

    @Override
    public PayInfoRespDTO getPayInfoByPaySn(String paySn) {
        return null;
    }

    @Override
    public RefundRespDTO commonRefund(RefundReqDTO requestParam) {
        return null;
    }
}
