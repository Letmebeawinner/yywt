package com.oa.controller.activiti;

import com.google.gson.Gson;
import com.oa.biz.news.OaNewsBiz;
import com.oa.entity.news.OaNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 信息发布状态
 *
 * @author lzh
 * @create 2018-01-02-14:54
 */
@Component
public class NewsState implements State {

    private static final String oaNewsApplyHistory = "/news/oa_news_apply_history";

    @Autowired
    private OaNewsBiz oaNewsBiz;

    private static NewsState newsState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaNews oaNews = newsState.oaNewsBiz.getOaNewsByProcessInstanceId(processInstanceId);
        Gson gson = new Gson();

        request.setAttribute("oaNews", oaNews);

        if (oaNews.getPublicWay() != null) {
            String[] publicWay = (oaNews.getPublicWay()).split(",");
            request.setAttribute("publicWay", gson.toJson(publicWay));
        }
        if (oaNews.getNotAllowReason() != null) {
            String[] notAllowReason = oaNews.getNotAllowReason().split(",");
            request.setAttribute("notAllowReason", gson.toJson(notAllowReason));
        }

        return oaNewsApplyHistory;
    }

    @PostConstruct
    public void init () {
        newsState = this;
    }
}