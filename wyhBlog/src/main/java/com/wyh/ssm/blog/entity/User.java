package com.wyh.ssm.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 50, message = "用户名长度必须在 2-50 个字符之间")
    private String userName;

    @JsonIgnore
    private String userPass;

    @Size(max = 50, message = "昵称长度不能超过 50 个字符")
    private String userNickname;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过 100 个字符")
    private String userEmail;

    @Size(max = 255, message = "个人网站长度不能超过 255 个字符")
    private String userUrl;

    @Size(max = 255, message = "头像地址长度不能超过 255 个字符")
    private String userAvatar;

    @Size(max = 50, message = "最后登录 IP 长度不能超过 50 个字符")
    private String userLastLoginIp;

    private Date userRegisterTime;

    private Date userLastLoginTime;

    private Integer userStatus;

    private Integer userRole;

    /**
     * 文章数量（非数据库字段）
     */
    @JsonIgnore
    private Integer articleCount;

    /**
     * 默认用户（用于未分类等情况）
     */
    public static User defaultUser() {
        return User.builder()
                .userId(0)
                .userName("default")
                .userNickname("默认用户")
                .build();
    }
}
