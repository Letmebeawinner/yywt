package com.menhu.api;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.menhu.biz.article.ArticleBiz;
import com.menhu.entity.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class ApiArticleController extends BaseController {

    @Autowired
    private ArticleBiz articleBiz;

    /**
     * 查看通知公告
     *
     * @param id
     * @return
     */
    @RequestMapping("/queryArticleList")
    @ResponseBody
    public Map<String, Object> queryNoticeList(@RequestParam("id") Long id, @ModelAttribute("pagination") Pagination pagination) {
        Map<String, Object> json = null;
        try {
            List<Article> articleList = articleBiz.find(pagination, "typeId=" + id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, articleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 查看文章详细通过id
     *
     * @param id
     * @return
     */
    @RequestMapping("/queryArticleById")
    @ResponseBody
    public Map<String, Object> queryArticleById(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Article article = articleBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
