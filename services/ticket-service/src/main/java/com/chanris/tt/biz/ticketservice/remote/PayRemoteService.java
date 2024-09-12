package com.chanris.tt.biz.ticketservice.remote;

import com.chanris.tt.biz.ticketservice.remote.dto.PayInfoRespDTO;
import com.chanris.tt.biz.ticketservice.remote.dto.RefundReqDTO;
import com.chanris.tt.biz.ticketservice.remote.dto.RefundRespDTO;
import com.chanris.tt.framework.starter.convention.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description 支付单远程调用服务
 */
@FeignClient(value = "tt-pay${unique-name:}-service", url = "${aggregation.remote-url:}")
public interface PayRemoteService {
    /**
     * 支付单详情查询
     */
    @GetMapping("/api/pay-service/pay/query")
    Result<PayInfoRespDTO> getPayInfo(@RequestParam(value = "orderSn") String orderSn);

    /**
     * 公共退款接口
     */
    @PostMapping("/api/pay-service/common/refund")
    Result<RefundRespDTO> commonRefund(@RequestBody RefundReqDTO requestParam);
}
