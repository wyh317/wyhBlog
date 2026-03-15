package com.wyh.ssm.blog.exception;

/**
 * 资源未找到异常
 */
public class ResourceNotFoundException extends BusinessException {
    
    public ResourceNotFoundException(String resource) {
        super(404, resource + " 未找到");
    }
    
    public ResourceNotFoundException(String resource, Long id) {
        super(404, resource + " [ID=" + id + "] 未找到");
    }
}
