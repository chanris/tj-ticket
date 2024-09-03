package com.chanris.tt.biz.userservice.dto.req;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 乘车人添加&修改请求参数
 */
@Data
public class PassengerReqDTO {
    /**
     * 乘车人id
     */
    private String id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 证件类型
     */
    private Integer idType;

    /**
     * 证件号码
     */
    private String idCard;

    /**
     * 优惠类型
     */
    private Integer discountType;

    /**
     * 手机号
     */
    private String phone;
}
