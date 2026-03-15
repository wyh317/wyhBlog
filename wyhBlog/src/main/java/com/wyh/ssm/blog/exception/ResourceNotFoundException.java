package com.wyh.ssm.blog.exception;

/**
 * 资源未找到异常
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resourceName) {
        super(404, resourceName + " 未找到");
    }

    public ResourceNotFoundException(String resourceName, Long resourceId) {
        super(404, resourceName + " 未找到，ID: " + resourceId);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(404, message, cause);
    }
}
