package com.wyh.ssm.blog.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户登录请求 DTO
 */
@Data
public class LoginRequest {

    @NotBlank(message = "用户名或邮箱不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 是否记住我
     */
    private Boolean rememberMe = false;
}
