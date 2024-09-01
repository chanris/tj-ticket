package com.chanris.tt.framework.starter.convention.exception;

import com.chanris.tt.framework.starter.convention.errorcode.BaseErrorCode;
import com.chanris.tt.framework.starter.convention.errorcode.IErrorCode;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description 客户端异常
 */
public class ClientException extends AbstractException {
    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
