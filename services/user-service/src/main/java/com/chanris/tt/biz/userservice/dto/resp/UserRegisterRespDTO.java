package com.chanris.tt.biz.userservice.dto.resp;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户注册返回参数
 */
@Data
public class UserRegisterRespDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;
}
