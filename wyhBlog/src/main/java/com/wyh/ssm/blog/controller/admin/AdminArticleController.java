package com.wyh.ssm.blog.controller.admin;

import com.wyh.ssm.blog.controller.BaseController;
import com.wyh.ssm.blog.dto.ApiResponse;
import com.wyh.ssm.blog.dto.request.ArticleRequest;
import com.wyh.ssm.blog.dto.response.ArticleResponse;
import com.wyh.ssm.blog.dto.response.PageResult;
import com.wyh.ssm.blog.entity.Article;
import com.wyh.ssm.blog.entity.Category;
import com.wyh.ssm.blog.entity.Tag;
import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.service.ArticleService;
import com.wyh.ssm.blog.service.CategoryService;
import com.wyh.ssm.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台文章管理 REST Controller
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/articles")
public class AdminArticleController extends BaseController {

    private final ArticleService articleService;
    private final TagService tagService;
    private final CategoryService categoryService;

    /**
     * 获取文章列表（分页）
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResult<ArticleResponse>> listArticles(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status) {

        HashMap<String, Object> criteria = new HashMap<>();
        if (status != null) {
            criteria.put("status", status);
        }

        PageResult<Article> pageResult = articleService.pageArticle(pageNum, pageSize, criteria);
        List<ArticleResponse> articleResponses = pageResult.getList().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        PageResult<ArticleResponse> responsePage = PageResult.<ArticleResponse>builder()
                .pageNum(pageResult.getPageNum())
                .pageSize(pageResult.getPageSize())
                .total(pageResult.getTotal())
                .pages(pageResult.getPages())
                .list(articleResponses)
                .hasPrevious(pageResult.getHasPrevious())
                .hasNext(pageResult.getHasNext())
                .build();

        return ApiResponse.success(responsePage);
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ArticleResponse> getArticle(@PathVariable Integer id) {
        Article article = articleService.getArticleByStatusAndId(null, id);
        if (article == null) {
            return ApiResponse.notFound("文章不存在");
        }
        return ApiResponse.success(convertToResponse(article));
    }

    /**
     * 创建文章
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ArticleResponse> createArticle(
            @Validated @RequestBody ArticleRequest request,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ApiResponse.unauthorized("请先登录");
        }

        Article article = Article.builder()
                .articleUserId(user.getUserId())
                .articleTitle(request.getTitle())
                .articleContent(request.getContent())
                .articleSummary(request.getSummary())
                .articleStatus(request.getStatus())
                .articleIsComment(request.getAllowComment())
                .articleOrder(request.getOrder())
                .build();

        // 设置分类
        if (request.getCategoryIds() != null) {
            List<Category> categoryList = request.getCategoryIds().stream()
                    .map(Category::new)
                    .collect(Collectors.toList());
            article.setCategoryList(categoryList);
        }

        // 设置标签
        if (request.getTagIds() != null) {
            List<Tag> tagList = request.getTagIds().stream()
                    .map(Tag::new)
                    .collect(Collectors.toList());
            article.setTagList(tagList);
        }

        Article created = articleService.insertArticle(article);
        return ApiResponse.success("文章创建成功", convertToResponse(created));
    }

    /**
     * 更新文章
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ArticleResponse> updateArticle(
            @PathVariable Integer id,
            @Validated @RequestBody ArticleRequest request) {

        Article existingArticle = articleService.getArticleByStatusAndId(null, id);
        if (existingArticle == null) {
            return ApiResponse.notFound("文章不存在");
        }

        Article article = Article.builder()
                .articleId(id)
                .articleTitle(request.getTitle())
                .articleContent(request.getContent())
                .articleSummary(request.getSummary())
                .articleStatus(request.getStatus())
                .articleIsComment(request.getAllowComment())
                .articleOrder(request.getOrder())
                .build();

        // 设置分类
        if (request.getCategoryIds() != null) {
            List<Category> categoryList = request.getCategoryIds().stream()
                    .map(Category::new)
                    .collect(Collectors.toList());
            article.setCategoryList(categoryList);
        }

        // 设置标签
        if (request.getTagIds() != null) {
            List<Tag> tagList = request.getTagIds().stream()
                    .map(Tag::new)
                    .collect(Collectors.toList());
            article.setTagList(tagList);
        }

        articleService.updateArticleDetail(article);
        return ApiResponse.success("文章更新成功", convertToResponse(article));
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ApiResponse.success("文章删除成功", null);
    }

    /**
     * 批量删除文章
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteArticles(@RequestBody List<Integer> ids) {
        articleService.deleteArticleBatch(ids);
        return ApiResponse.success("批量删除成功", null);
    }

    /**
     * 获取创建文章页面所需数据（分类和标签）
     */
    @GetMapping("/new/form")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getCreateFormData() {
        List<Category> categories = categoryService.listCategory();
        List<Tag> tags = tagService.listTag();
        return ApiResponse.success(new FormDTO(categories, tags));
    }

    /**
     * 获取编辑文章页面所需数据
     */
    @GetMapping("/{id}/form")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ArticleFormDTO> getEditFormData(@PathVariable Integer id) {
        Article article = articleService.getArticleByStatusAndId(null, id);
        if (article == null) {
            return ApiResponse.notFound("文章不存在");
        }

        List<Category> categories = categoryService.listCategory();
        List<Tag> tags = tagService.listTag();

        List<Integer> categoryIds = new ArrayList<>();
        if (article.getCategoryList() != null) {
            categoryIds = article.getCategoryList().stream()
                    .map(Category::getCategoryId)
                    .collect(Collectors.toList());
        }

        List<Integer> tagIds = new ArrayList<>();
        if (article.getTagList() != null) {
            tagIds = article.getTagList().stream()
                    .map(Tag::getTagId)
                    .collect(Collectors.toList());
        }

        return ApiResponse.success(new ArticleFormDTO(
                article.getArticleTitle(),
                article.getArticleContent(),
                article.getArticleSummary(),
                article.getArticleStatus(),
                article.getArticleIsComment(),
                article.getArticleOrder(),
                categoryIds,
                tagIds,
                categories,
                tags
        ));
    }

    /**
     * 文章响应转换
     */
    private ArticleResponse convertToResponse(Article article) {
        ArticleResponse.AuthorInfo author = null;
        if (article.getUser() != null) {
            User user = article.getUser();
            author = ArticleResponse.AuthorInfo.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .nickname(user.getUserNickname())
                    .avatar(user.getUserAvatar())
                    .build();
        }

        List<ArticleResponse.CategoryInfo> categories = null;
        if (article.getCategoryList() != null) {
            categories = article.getCategoryList().stream()
                    .map(c -> ArticleResponse.CategoryInfo.builder()
                            .categoryId(c.getCategoryId())
                            .categoryName(c.getCategoryName())
                            .description(c.getCategoryDescription())
                            .build())
                    .collect(Collectors.toList());
        }

        List<ArticleResponse.TagInfo> tags = null;
        if (article.getTagList() != null) {
            tags = article.getTagList().stream()
                    .map(t -> ArticleResponse.TagInfo.builder()
                            .tagId(t.getTagId())
                            .tagName(t.getTagName())
                            .build())
                    .collect(Collectors.toList());
        }

        return ArticleResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getArticleTitle())
                .summary(article.getArticleSummary())
                .content(article.getArticleContent())
                .viewCount(article.getArticleViewCount())
                .commentCount(article.getArticleCommentCount())
                .likeCount(article.getArticleLikeCount())
                .createTime(article.getArticleCreateTime())
                .updateTime(article.getArticleUpdateTime())
                .status(article.getArticleStatus())
                .allowComment(article.getArticleIsComment())
                .order(article.getArticleOrder())
                .author(author)
                .categories(categories)
                .tags(tags)
                .build();
    }

    /**
     * 表单数据传输对象
     */
    @lombok.Data
    @lombok.Builder
    private static class FormDTO {
        private List<Category> categories;
        private List<Tag> tags;
    }

    /**
     * 文章表单数据传输对象
     */
    @lombok.Data
    @lombok.Builder
    private static class ArticleFormDTO {
        private String title;
        private String content;
        private String summary;
        private Integer status;
        private Integer allowComment;
        private Integer order;
        private List<Integer> categoryIds;
        private List<Integer> tagIds;
        private List<Category> categories;
        private List<Tag> tags;
    }
}
