package com.chanris.tt.biz.payservice.service.impl;

import com.chanris.tt.biz.payservice.dto.RefundReqDTO;
import com.chanris.tt.biz.payservice.dto.RefundRespDTO;
import com.chanris.tt.biz.payservice.service.RefundService;
import org.springframework.stereotype.Service;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 退款接口层实现
 */
@Service
public class RefundServiceImpl implements RefundService {
    @Override
    public RefundRespDTO commonRefund(RefundReqDTO requestParam) {
        return null;
    }
}
