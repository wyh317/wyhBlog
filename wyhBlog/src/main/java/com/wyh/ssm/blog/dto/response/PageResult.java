package com.wyh.ssm.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 分页响应 DTO
 *
 * @param <T> 数据类型
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResult<T> {

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 是否有上一页（getter 方法）
     */
    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    /**
     * 是否有下一页（getter 方法）
     */
    public Boolean getHasNext() {
        return hasNext;
    }
}
