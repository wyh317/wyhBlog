package com.wyh.ssm.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 文章响应 DTO
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponse {

    private Integer articleId;

    private String title;

    private String summary;

    private String content;

    private Integer viewCount;

    private Integer commentCount;

    private Integer likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer status;

    private Integer allowComment;

    private Integer order;

    private AuthorInfo author;

    private List<CategoryInfo> categories;

    private List<TagInfo> tags;

    /**
     * 作者信息
     */
    @Data
    @Builder
    public static class AuthorInfo {
        private Integer userId;
        private String userName;
        private String nickname;
        private String avatar;
    }

    /**
     * 分类信息
     */
    @Data
    @Builder
    public static class CategoryInfo {
        private Integer categoryId;
        private String categoryName;
        private String description;
    }

    /**
     * 标签信息
     */
    @Data
    @Builder
    public static class TagInfo {
        private Integer tagId;
        private String tagName;
    }
}
