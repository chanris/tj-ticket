package com.chanris.tt.framework.starter.convention.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 全部返回对象
 */
@Data
@Accessors(chain = true) // 允许 setter 方法返回当前对象 (this) 而不是 void，从而支持链式调用。
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 5679018624309023727L;

    /**
     * 正确返回码
     */
    public static final String SUCCESS_CODE = "0";

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 请求ID
     */
    private String requestId;

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }
}
