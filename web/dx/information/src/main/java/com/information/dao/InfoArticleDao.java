package com.information.dao;

import com.a_268.base.core.BaseDao;
import com.information.entity.InfoArticle;

public interface InfoArticleDao extends BaseDao<InfoArticle> {

    void addInfoArticle(InfoArticle infoArticle);

    void delInfoArticle(Long id);

}
