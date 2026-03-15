package com.wyh.ssm.blog.service.impl;

import cn.hutool.http.HtmlUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyh.ssm.blog.dto.response.PageResult;
import com.wyh.ssm.blog.entity.Article;
import com.wyh.ssm.blog.entity.ArticleCategoryRef;
import com.wyh.ssm.blog.entity.ArticleTagRef;
import com.wyh.ssm.blog.entity.Category;
import com.wyh.ssm.blog.entity.Tag;
import com.wyh.ssm.blog.enums.ArticleCommentStatus;
import com.wyh.ssm.blog.exception.ResourceNotFoundException;
import com.wyh.ssm.blog.exception.ValidationException;
import com.wyh.ssm.blog.mapper.ArticleCategoryRefMapper;
import com.wyh.ssm.blog.mapper.ArticleMapper;
import com.wyh.ssm.blog.mapper.ArticleTagRefMapper;
import com.wyh.ssm.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文章服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleCategoryRefMapper articleCategoryRefMapper;
    private final ArticleTagRefMapper articleTagRefMapper;

    private static final int SUMMARY_LENGTH = 150;

    @Override
    public Integer countArticle(Integer status) {
        return articleMapper.countArticle(status);
    }

    @Override
    public Integer countArticleComment() {
        return articleMapper.countArticleComment();
    }

    @Override
    public Integer countArticleView() {
        return articleMapper.countArticleView();
    }

    @Override
    public Integer countArticleByCategoryId(Integer categoryId) {
        return articleCategoryRefMapper.countArticleByCategoryId(categoryId);
    }

    @Override
    public Integer countArticleByTagId(Integer tagId) {
        return articleTagRefMapper.countArticleByTagId(tagId);
    }

    @Override
    public List<Article> listArticle(HashMap<String, Object> criteria) {
        return articleMapper.findAll(criteria);
    }

    @Override
    public List<Article> listRecentArticle(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        return articleMapper.listArticleByLimit(limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleDetail(Article article) {
        validateArticle(article);
        article.setArticleUpdateTime(new Date());
        articleMapper.update(article);

        // 更新标签关联
        if (article.getTagList() != null) {
            articleTagRefMapper.deleteByArticleId(article.getArticleId());
            for (Tag tag : article.getTagList()) {
                if (tag.getTagId() != null) {
                    ArticleTagRef ref = new ArticleTagRef(article.getArticleId(), tag.getTagId());
                    articleTagRefMapper.insert(ref);
                }
            }
        }

        // 更新分类关联
        if (article.getCategoryList() != null) {
            articleCategoryRefMapper.deleteByArticleId(article.getArticleId());
            for (Category category : article.getCategoryList()) {
                if (category.getCategoryId() != null) {
                    ArticleCategoryRef ref = new ArticleCategoryRef(article.getArticleId(), category.getCategoryId());
                    articleCategoryRefMapper.insert(ref);
                }
            }
        }

        log.info("文章已更新，articleId: {}", article.getArticleId());
    }

    @Override
    public void updateArticle(Article article) {
        if (article.getArticleId() == null) {
            throw new ValidationException("文章 ID 不能为空");
        }
        articleMapper.update(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticleBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ValidationException("文章 ID 列表不能为空");
        }
        articleMapper.deleteBatch(ids);
        log.info("批量删除文章，ids: {}", ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(Integer id) {
        if (id == null) {
            throw new ValidationException("文章 ID 不能为空");
        }
        Article article = getArticleByStatusAndId(null, id);
        if (article == null) {
            throw new ResourceNotFoundException("文章", id.longValue());
        }

        articleMapper.deleteById(id);
        articleCategoryRefMapper.deleteByArticleId(id);
        articleTagRefMapper.deleteByArticleId(id);
        log.info("文章已删除，articleId: {}", id);
    }

    @Override
    public PageResult<Article> pageArticle(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria) {
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        PageHelper.startPage(pageIndex, pageSize);
        List<Article> articleList = articleMapper.findAll(criteria);

        // 填充分类信息
        for (Article article : articleList) {
            List<Category> categoryList = articleCategoryRefMapper.listCategoryByArticleId(article.getArticleId());
            if (categoryList == null || categoryList.isEmpty()) {
                categoryList = new ArrayList<>();
                categoryList.add(Category.Default());
            }
            article.setCategoryList(categoryList);
        }

        PageInfo<Article> pageInfo = new PageInfo<>(articleList);

        // 计算是否有上一页和下一页
        boolean hasPrevious = pageInfo.getPageNum() > 1;
        boolean hasNext = pageInfo.getPageNum() < pageInfo.getPages();

        return PageResult.<Article>builder()
                .pageNum(pageInfo.getPageNum())
                .pageSize(pageInfo.getPageSize())
                .total(pageInfo.getTotal())
                .pages(pageInfo.getPages())
                .list(pageInfo.getList())
                .hasPrevious(hasPrevious)
                .hasNext(hasNext)
                .build();
    }

    @Override
    public Article getArticleByStatusAndId(Integer status, Integer id) {
        if (id == null) {
            throw new ValidationException("文章 ID 不能为空");
        }
        Article article = articleMapper.getArticleByStatusAndId(status, id);
        if (article != null) {
            List<Category> categoryList = articleCategoryRefMapper.listCategoryByArticleId(article.getArticleId());
            List<Tag> tagList = articleTagRefMapper.listTagByArticleId(article.getArticleId());
            article.setCategoryList(categoryList);
            article.setTagList(tagList);
        }
        return article;
    }

    @Override
    public List<Article> listArticleByViewCount(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        return articleMapper.listArticleByViewCount(limit);
    }

    @Override
    public Article getAfterArticle(Integer id) {
        return articleMapper.getAfterArticle(id);
    }

    @Override
    public Article getPreArticle(Integer id) {
        return articleMapper.getPreArticle(id);
    }

    @Override
    public List<Article> listRandomArticle(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        return articleMapper.listRandomArticle(limit);
    }

    @Override
    public List<Article> listArticleByCommentCount(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        return articleMapper.listArticleByCommentCount(limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Article insertArticle(Article article) {
        validateArticle(article);

        // 生成摘要
        if (!StringUtils.hasText(article.getArticleSummary()) && StringUtils.hasText(article.getArticleContent())) {
            String summaryText = HtmlUtil.cleanHtmlTag(article.getArticleContent());
            article.setArticleSummary(summaryText.length() > SUMMARY_LENGTH
                    ? summaryText.substring(0, SUMMARY_LENGTH)
                    : summaryText);
        }

        article.setArticleCreateTime(new Date());
        article.setArticleUpdateTime(new Date());
        article.setArticleIsComment(ArticleCommentStatus.ALLOW.getValue());
        article.setArticleViewCount(0);
        article.setArticleLikeCount(0);
        article.setArticleOrder(article.getArticleOrder() != null ? article.getArticleOrder() : 1);

        articleMapper.insert(article);

        // 添加分类关联
        if (article.getCategoryList() != null) {
            for (Category category : article.getCategoryList()) {
                if (category.getCategoryId() != null) {
                    ArticleCategoryRef ref = new ArticleCategoryRef(article.getArticleId(), category.getCategoryId());
                    articleCategoryRefMapper.insert(ref);
                }
            }
        }

        // 添加标签关联
        if (article.getTagList() != null) {
            for (Tag tag : article.getTagList()) {
                if (tag.getTagId() != null) {
                    ArticleTagRef ref = new ArticleTagRef(article.getArticleId(), tag.getTagId());
                    articleTagRefMapper.insert(ref);
                }
            }
        }

        log.info("文章已创建，articleId: {}", article.getArticleId());
        return article;
    }

    @Override
    public void updateCommentCount(Integer articleId) {
        articleMapper.updateCommentCount(articleId);
    }

    @Override
    public Article getLastUpdateArticle() {
        return articleMapper.getLastUpdateArticle();
    }

    @Override
    public List<Article> listArticleByCategoryId(Integer cateId, Integer limit) {
        return articleMapper.findArticleByCategoryId(cateId, limit);
    }

    @Override
    public List<Article> listArticleByCategoryIds(List<Integer> cateIds, Integer limit) {
        if (cateIds == null || cateIds.isEmpty()) {
            return new ArrayList<>();
        }
        return articleMapper.findArticleByCategoryIds(cateIds, limit);
    }

    @Override
    public List<Integer> listCategoryIdByArticleId(Integer articleId) {
        return articleCategoryRefMapper.selectCategoryIdByArticleId(articleId);
    }

    @Override
    public List<Article> listAllNotWithContent() {
        return articleMapper.listAllNotWithContent();
    }

    /**
     * 验证文章数据
     */
    private void validateArticle(Article article) {
        if (article == null) {
            throw new ValidationException("文章信息不能为空");
        }
        if (!StringUtils.hasText(article.getArticleTitle())) {
            throw new ValidationException("文章标题不能为空");
        }
        if (article.getArticleTitle().length() > 200) {
            throw new ValidationException("文章标题长度不能超过 200 个字符");
        }
        if (!StringUtils.hasText(article.getArticleContent())) {
            throw new ValidationException("文章内容不能为空");
        }
    }
}
