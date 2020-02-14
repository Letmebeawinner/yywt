package com.menhu.biz.common;

import com.a_268.base.core.Pagination;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menhu.biz.article.ArticleBiz;
import com.menhu.biz.article.ArticleTypeBiz;
import com.menhu.entity.article.Article;
import com.menhu.entity.article.ArticleType;
import com.menhu.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzh
 * @create 2018-01-02-19:24
 */
@Service
public class MenHuHessianBiz implements MenHuHessianService {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    private ArticleTypeBiz articleTypeBiz;
    @Autowired
    private ArticleBiz articleBiz;

    @Override
    public Map<String, Object> getArticleType(Pagination pagination, String whereSql) {
        Map<String, Object> json = new HashMap<>();
        whereSql = " status="+ Status.正常数据.getStatus();

        List<ArticleType> articleTypeList = articleTypeBiz.find(pagination, whereSql);
        json.put("pagination", gson.toJson(pagination));
        json.put("articleTypeList", gson.toJson(articleTypeList));
        return json;
    }

    @Override
    public Map<String, Object> addArticle(Map<String, String> articleMap) {
        Map<String, Object> json = new HashMap<>();
        Article article = new Article();
        article.setAuthor(articleMap.get("author"));
        article.setContent(articleMap.get("content"));
        article.setTypeId(Long.parseLong(articleMap.get("typeId")));
        article.setSource(articleMap.get("source"));
        article.setTitle(articleMap.get("title"));
        article.setFileUrl(articleMap.get("fileUrl"));
        article.setPicture("");
        article.setStatus(0);
        articleBiz.save(article);
        json.put("article", gson.toJson(article));
        return json;
    }

    @Override
    public int delArticleById(Long id) {
        articleBiz.deleteById(id);
        return 1;
    }
}
