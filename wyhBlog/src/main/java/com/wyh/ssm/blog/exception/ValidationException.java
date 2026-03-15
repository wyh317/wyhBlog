package com.wyh.ssm.blog.exception;

/**
 * 参数验证异常
 */
public class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(400, message);
    }

    public ValidationException(String field, String message) {
        super(400, field + ": " + message);
    }
}
