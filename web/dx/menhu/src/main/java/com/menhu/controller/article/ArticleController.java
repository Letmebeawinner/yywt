package com.menhu.controller.article;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.menhu.biz.article.ArticleBiz;
import com.menhu.biz.article.ArticleTypeBiz;
import com.menhu.entity.article.Article;
import com.menhu.entity.article.ArticleApp;
import com.menhu.enums.ArticleEnum;
import com.menhu.enums.Status;
import com.menhu.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资讯控制类
 *
 * @author guoshiqi
 * @create 2016-12-09-12:54
 */
@Controller
@RequestMapping("/front/menhu/article")
public class ArticleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private static final String article_list = "front/article/article_list";
    private static final String article_info = "front/article/article_info";

    @Resource
    private ArticleBiz articleBiz;

    @InitBinder({"article"})
    public void initArticle(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("article.");
    }

    /**
     * @Description:某类型列表
     */
    @RequestMapping("/queryArticleList")
    public String queryArticleList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("article") Article article) {
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);

            String whereSql = GenerateSqlUtil.getSql(article);
            List<Article> articleList = articleBiz.find(pagination, whereSql);
            request.setAttribute("articleList", articleList);


            List<Article> hotArticle=articleBiz.find(null," status!=1 and typeId="+article.getTypeId()+" order by clickTimes desc limit 10");
            request.setAttribute("hotArticle",hotArticle);
        } catch (Exception e) {
            logger.info("ArticleController-queryArticleList", e);
            return this.setErrorPath(request, e);
        }
        return article_list;
    }


    /**
     * @Description:查询某个资讯
     */
    @RequestMapping("/queryArticleInfo")
    public String queryArticleInfo(HttpServletRequest request, @RequestParam("typeId") Long typeId, @RequestParam("id") Long id) {
        try {
            Article _article = new Article();
            _article.setTypeId(typeId);
            _article.setId(id);
            Article article = articleBiz.findById(id);
            if(ObjectUtils.isNotNull(article.getFileUrl()) && !"".equals(article.getFileUrl())){
                article.setContent(article.getContent()+"<br/><a style='text-decoration: underline;color: blue;' href='"+article.getFileUrl()+"'>点击下载附件内容</a>");
            }
            request.setAttribute("article", article);

            List<Article> hotArticle=articleBiz.find(null," status!=1 and typeId="+typeId+" order by clickTimes desc limit 10");
            request.setAttribute("hotArticle",hotArticle);


        } catch (Exception e) {
            logger.info("ArticleController-queryArticleInfo", e);
            return this.setErrorPath(request, e);
        }
        return article_info;
    }


    /**
     * 修改文章点击数量
     *
     * @return
     */
    @RequestMapping("/updateClickTimes")
    public Map<String, Object> updateClickTimes(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {

            Article article = articleBiz.findById(id);
            article.setId(id);
            article.setClickTimes(article.getClickTimes() + 1);
            articleBiz.update(article);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, "");
    }

}


