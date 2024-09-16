package com.chanris.tt.biz.orderservice.dto.req;

import com.chanris.tt.framework.starter.convention.page.PageRequest;
import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/5
 * @description 车票订单分页查询
 */
@Data
public class TicketOrderPageQueryReqDTO extends PageRequest {

    /**
     * 用户唯一标识
     */
    private String userId;

    /**
     * 状态类型：0：未完成 1：未出现 2：历史订单
     */
    private Integer statusType;
}
