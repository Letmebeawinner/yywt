package com.oa.biz.article;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.dao.article.ArticleDao;
import com.oa.entity.article.Article;
import com.oa.entity.article.ArticleDto;
import com.oa.entity.article.ArticleType;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章service
 *
 * @author wanghailong
 * @create 2017-04-12-下午 5:45
 */
@Service
public class ArticleBiz extends BaseBiz<Article,ArticleDao> {

    @Autowired
    private ArticleTypeBiz articleTypeBiz;

    public List<ArticleDto> getArticleDtos(Pagination pagination, Article article) {
        String whereSql = GenerateSqlUtil.getSql(article);
         List<Article> articles= this.find(pagination,whereSql);
        List<ArticleDto> articleDtos = null;
        articleDtos = articles.stream()
                .map(article1 -> convertNewsToDto(article1))
                .collect(Collectors.toList());
        return articleDtos;
    }

    private ArticleDto convertNewsToDto(Article article) {
        ArticleDto articleDto= new ArticleDto();
        BeanUtils.copyProperties(article,articleDto);
        if (articleDto.getType_id() != null) {
            ArticleType articleType=articleTypeBiz.findById(articleDto.getType_id().longValue());
            if (articleType != null) {
                articleDto.setNewArticleTypeName(articleType.getType_name());
            }
        }
        return articleDto;
    }
}
