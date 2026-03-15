package com.wyh.ssm.blog.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 文章创建/更新请求 DTO
 */
@Data
public class ArticleRequest {

    private Integer articleId;

    @NotBlank(message = "文章标题不能为空")
    @Size(max = 200, message = "文章标题长度不能超过 200 个字符")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    @Size(max = 500, message = "文章摘要长度不能超过 500 个字符")
    private String summary;

    /**
     * 是否开启评论：1-开启，0-关闭
     */
    @NotNull(message = "评论设置不能为空")
    private Integer allowComment = 1;

    /**
     * 文章状态：1-公开，2-私密，3-草稿
     */
    @NotNull(message = "文章状态不能为空")
    private Integer status = 1;

    /**
     * 文章顺序
     */
    private Integer order = 1;

    /**
     * 分类 ID 列表
     */
    @NotEmpty(message = "文章至少需要一个分类")
    private List<Integer> categoryIds;

    /**
     * 标签 ID 列表
     */
    private List<Integer> tagIds;
}
