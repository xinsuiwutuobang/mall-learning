package com.macro.mall.tiny.exception;

import com.macro.mall.tiny.common.api.IErrorCode;

/**
 * <p>
 *  retrofit远程调用异常
 *  异常发生在单字符串类型转换为数组对象等类型时抛出异常，重构异常
 * </p>
 *
 * @author yangfei
 * @since 2019-05-18
 */
public class RetrofitException extends RuntimeException{
    private IErrorCode errorCode;


    public RetrofitException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public RetrofitException(String message) {
        super(message);
    }

    public RetrofitException(Throwable cause) {
        super(cause);
    }

    public RetrofitException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
