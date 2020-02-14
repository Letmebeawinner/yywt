package com.menhu.biz.common;


import com.a_268.base.core.Pagination;

import java.util.Map;

/**
 * 门户hessian
 *
 * @author lzh
 * @create 2018-01-02-19:14
 */
public interface MenHuHessianService {

    /**
     * 查询所有的资讯类型
     * @param pagination
     * @param whereSql
     * @return
     */
    Map<String, Object> getArticleType(Pagination pagination, String whereSql);

    /**
     * 增加资讯
     * @param articleMap
     * @return
     */
    Map<String, Object> addArticle(Map<String, String> articleMap);

    /**
     * 根据id删除资讯
     * @param id
     * @return
     */
    int delArticleById(Long id);
}
