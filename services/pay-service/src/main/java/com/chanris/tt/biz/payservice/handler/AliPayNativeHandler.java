package com.chanris.tt.biz.payservice.handler;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.chanris.tt.biz.payservice.common.enums.PayChannelEnum;
import com.chanris.tt.biz.payservice.common.enums.PayTradeTypeEnum;
import com.chanris.tt.biz.payservice.config.AliPayProperties;
import com.chanris.tt.biz.payservice.dto.base.AliPayRequest;
import com.chanris.tt.biz.payservice.dto.base.PayRequest;
import com.chanris.tt.biz.payservice.dto.base.PayResponse;
import com.chanris.tt.biz.payservice.handler.base.AbstractPayHandler;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;
import com.chanris.tt.framework.starter.convention.exception.ServiceException;
import com.chanris.tt.framework.starter.designpattern.strategy.AbstractExecuteStrategy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/13
 * @description 阿里支付组件
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliPayNativeHandler extends AbstractPayHandler implements AbstractExecuteStrategy<PayRequest, PayResponse> {

    private final AliPayProperties aliPayProperties;

    @SneakyThrows(value = AlipayApiException.class)
    @Override
    @Retryable(value = ServiceException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public PayResponse pay(PayRequest payRequest) {
        AliPayRequest aliPayRequest = payRequest.getAliPayRequest();
        AlipayConfig alipayConfig = BeanUtil.convert(aliPayProperties, AlipayConfig.class);
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(aliPayRequest.getOrderSn());
        model.setTotalAmount(aliPayRequest.getTotalAmount().toString());
        model.setSubject(aliPayRequest.getSubject());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayProperties.getNotifyUrl());
        request.setBizModel(model);
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            log.info("发起支付宝支付，订单号：{}，子订单号：{}，订单请求号：{}，订单金额：{} \n调用支付返回：\n\n{}\n",
                    aliPayRequest.getOrderSn(),
                    aliPayRequest.getOutOrderSn(),
                    aliPayRequest.getOrderRequestId(),
                    aliPayRequest.getTotalAmount(),
                    JSONObject.toJSONString(response));
            if (!response.isSuccess()) {
                throw new ServiceException("调用支付宝发起支付异常");
            }
            return new PayResponse(StrUtil.replace(StrUtil.replace(response.getBody(), "\"", "'"), "\n", ""));
        } catch (AlipayApiException ex) {
            throw new ServiceException("调用支付宝支付异常");
        }
    }

    @Override
    public String mark() {
        return StrBuilder.create()
                .append(PayChannelEnum.ALI_PAY.name())
                .append("_")
                .append(PayTradeTypeEnum.NATIVE.name())
                .toString();
    }

    @Override
    public PayResponse executeResp(PayRequest requestParam) {
        return pay(requestParam);
    }
}
