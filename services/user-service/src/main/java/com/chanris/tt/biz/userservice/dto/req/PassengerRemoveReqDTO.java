package com.chanris.tt.biz.userservice.dto.req;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 乘车人移除请求参数
 */
@Data
public class PassengerRemoveReqDTO {
    /**
     * 乘车人id
     */
    private String id;
}
