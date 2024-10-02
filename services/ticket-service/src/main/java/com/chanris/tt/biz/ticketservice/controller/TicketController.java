package com.chanris.tt.biz.ticketservice.controller;

import com.chanris.tt.biz.ticketservice.dto.req.CancelTicketOrderReqDTO;
import com.chanris.tt.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import com.chanris.tt.biz.ticketservice.dto.req.RefundTicketReqDTO;
import com.chanris.tt.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.RefundTicketRespDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.TicketPageQueryRespDTO;
import com.chanris.tt.biz.ticketservice.dto.resp.TicketPurchaseRespDTO;
import com.chanris.tt.biz.ticketservice.remote.dto.PayInfoRespDTO;
import com.chanris.tt.biz.ticketservice.service.TicketService;
import com.chanris.tt.framework.starter.convention.result.Result;
import com.chanris.tt.framework.starter.idempotent.annotation.Idempotent;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentSceneEnum;
import com.chanris.tt.framework.starter.idempotent.enums.IdempotentTypeEnum;
import com.chanris.tt.framework.starter.log.annotation.ILog;
import com.chanris.tt.framework.starter.web.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/8
 * @description
 */
@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    /**
     * 根据条件查询车票
     */
    @GetMapping("/api/ticket-service/ticket/query")
    public Result<TicketPageQueryRespDTO> pageListTicketQuery(TicketPageQueryReqDTO requestParam) {
        return Results.success(ticketService.pageListTicketQueryV1(requestParam));
    }

    /**
     * 购买车票
     */
    @ILog
    @Idempotent(
            uniqueKeyPrefix = "tt-ticket:lock_purchase-tickets:",
            key = "T(com.chanris.tt.framework.starter.bases.ApplicationContextHolder).getBean('environment').getProperty('unique-name', '')"
                    + "+'_'+"
                    + "T(com.chanris.tt.framework.starter.user.core.UserContext).getUsername()",
            message = "正在执行下单流程，请稍后...",
            scene = IdempotentSceneEnum.RESTAPI,
            type = IdempotentTypeEnum.SPEL
    )
    @PostMapping("/api/ticket-service/ticket/purchase")
    public Result<TicketPurchaseRespDTO> purchaseTickets(@RequestBody PurchaseTicketReqDTO requestParam) {
        return Results.success(ticketService.purchaseTicketsV1(requestParam));
    }

    /**
     * 购买车票v2
     *
     * 幂等用来做限流：
     * 一个用户的购买车票请求只有在被处理后，该用户才能继续发送下一个购买车票请求；
     * 当线程还在处理该用户上一个购票请求时，下一个购票逻辑进来时，会被幂等逻辑打回去，并返回信息：“正在执行下单流程，请稍后...”
     */
    @ILog
    @Idempotent(
            uniqueKeyPrefix = "tt-ticket:lock_purchase-tickets:",
            key = "T(com.chanris.tt.framework.starter.bases.ApplicationContextHolder).getBean('environment').getProperty('unique-name', '')"
                    + "+'_'+"
                    + "T(com.chanris.tt.framework.starter.user.core.UserContext).getUsername()",
            message = "正在执行下单流程，请稍后...",
            scene = IdempotentSceneEnum.RESTAPI,
            type = IdempotentTypeEnum.SPEL
    )
    @PostMapping("/api/ticket-service/ticket/purchase/v2")
    public Result<TicketPurchaseRespDTO> purchaseTicketsV2(@RequestBody PurchaseTicketReqDTO requestParam) {
        return Results.success(ticketService.purchaseTicketsV2(requestParam));
    }

    /**
     * 取消车票订单
     */
    @ILog
    @PostMapping("/api/ticket-service/ticket/cancel")
    public Result<Void> cancelTicketOrder(@RequestBody CancelTicketOrderReqDTO requestParam) {
        ticketService.cancelTicketOrder(requestParam);
        return Results.success();
    }

    /**
     * 支付单详情查询
     */
    @GetMapping("/api/ticket-service/ticket/pay/query")
    public Result<PayInfoRespDTO> getPayInfo(@RequestParam(value = "orderSn") String orderSn) {
        return Results.success(ticketService.getPayInfo(orderSn));
    }

    /**
     * 公共退款接口
     */
    @PostMapping("/api/ticket-service/ticket/refund")
    public Result<RefundTicketRespDTO> commonTicketRefund(@RequestBody RefundTicketReqDTO requestParam) {
        return Results.success(ticketService.commonTicketRefund(requestParam));
    }
}
