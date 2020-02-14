package com.oa.dao.news;

import com.a_268.base.core.BaseDao;
import com.oa.entity.news.News;
import com.oa.entity.news.OaNewsDto;

import java.util.List;

/**
 * @author lizhenhui
 * @create 2016-12-29 17:35
 */
public interface NewsDao extends BaseDao<News> {

    List<News> queryNewsStatistic();

    List<OaNewsDto> queryOaNewsStatistic();
}
