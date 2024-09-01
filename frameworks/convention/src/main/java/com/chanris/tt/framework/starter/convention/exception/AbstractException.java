package com.chanris.tt.framework.starter.convention.exception;

import com.chanris.tt.framework.starter.convention.errorcode.IErrorCode;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 抽象项目中三类异常体系，客户端异常、服务端异常以及远程服务调用异常
 */
public abstract class AbstractException extends RuntimeException {
    public final String errorCode;
    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = errorCode.message();
    }
}
