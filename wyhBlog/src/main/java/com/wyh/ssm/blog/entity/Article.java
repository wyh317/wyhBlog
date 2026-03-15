package com.wyh.ssm.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private Integer articleUserId;

    @NotBlank(message = "文章标题不能为空")
    @Size(max = 200, message = "文章标题长度不能超过 200 个字符")
    private String articleTitle;

    @JsonIgnore
    private String articleContent;

    @Size(max = 500, message = "文章摘要长度不能超过 500 个字符")
    private String articleSummary;

    @Builder.Default
    private Integer articleViewCount = 0;

    @Builder.Default
    private Integer articleCommentCount = 0;

    @Builder.Default
    private Integer articleLikeCount = 0;

    private Date articleCreateTime;

    private Date articleUpdateTime;

    @Builder.Default
    private Integer articleIsComment = 1;

    @Builder.Default
    private Integer articleStatus = 1;

    @Builder.Default
    private Integer articleOrder = 1;

    /**
     * 作者信息
     */
    private User user;

    /**
     * 标签列表
     */
    private List<Tag> tagList;

    /**
     * 分类列表
     */
    private List<Category> categoryList;

    /**
     * 上一篇文章
     */
    private Article preArticle;

    /**
     * 下一篇文章
     */
    private Article nextArticle;
}
