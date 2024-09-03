package com.chanris.tt.biz.userservice.dto.req;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户修改请求参数
 */
@Data
public class UserUpdateReqDTO {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 旅客类型
     */
    private Integer userType;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 地址
     */
    private String address;
}
