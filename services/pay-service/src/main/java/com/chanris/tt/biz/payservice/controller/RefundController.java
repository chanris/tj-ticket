package com.chanris.tt.biz.payservice.controller;

import com.chanris.tt.biz.payservice.dto.RefundReqDTO;
import com.chanris.tt.biz.payservice.dto.RefundRespDTO;
import com.chanris.tt.biz.payservice.service.RefundService;
import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 退款控制层
 */
@RestController
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    /**
     * 公共退款接口
     */
    @PostMapping("/api/pay-service/common/refund")
    public Result<RefundRespDTO> commonRefund(@RequestBody RefundReqDTO requestParam) {
        return Results.success(refundService.commonRefund(requestParam));
    }
}
