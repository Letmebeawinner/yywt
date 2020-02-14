package com.oa.controller.article;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.article.ArticleBiz;
import com.oa.biz.article.ArticleTypeBiz;
import com.oa.entity.article.Article;
import com.oa.entity.article.ArticleDto;
import com.oa.entity.article.ArticleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 天将降大任于斯人也
 *
 * @author wanghailong
 * @create 2017-04-12-下午 5:46
 */
@Controller
@RequestMapping("/admin/oa")
public class ArticleController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleBiz articleBiz;
    @Autowired
    private ArticleTypeBiz articleTypeBiz;

    private static final String toAddArticle = "/article/article_add";//添加文章类型
    private static final String toUpdateArticle = "/article/article_update";//修改文章类型
    private static final String articleList = "/article/article_list";//文章类型列表

    @InitBinder("article")
    public void initBinderArticle(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.setFieldDefaultPrefix("article.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true, 10));
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dateFormat, true, 19));
    }

    /**
     * 查询全部文章
     *
     * @param request
     * @param pagination
     * @param article
     * @return
     */
    @RequestMapping("/queryAllArticle")
    public String queryAllArticle(HttpServletRequest request,
                                  @ModelAttribute("pagination") Pagination pagination,
                                  @ModelAttribute("article") Article article) {
        pagination.setPageSize(10);
        pagination.setRequest(request);
        try {
           /* String whereSql = GenerateSqlUtil.getSql(article);
            List<ArticleType> articleTypeList = articleTypeBiz.findAll();
            List<Article> articleList = articleBiz.find(pagination, whereSql);
            request.setAttribute("articleList", articleList);
            request.setAttribute("articleTypeList", articleTypeList);*/
           List<ArticleDto> articleDtoList=articleBiz.getArticleDtos(pagination,article);
           request.setAttribute("articleDtoList",articleDtoList);
        } catch (Exception e) {
            logger.error("ArticleController--queryAllArticle", e);
        }
        return articleList;
    }

    /**
     * 跳转到添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddArticle")
    public String toAddArticle(HttpServletRequest request) {
        try {
            List<ArticleType> articleTypeList = articleTypeBiz.findAll();
            request.setAttribute("articleTypeList", articleTypeList);
        } catch (Exception e) {
            logger.error("ArticleController--toAddArticle", e);
        }
        return toAddArticle;
    }

    /**
     * 进行添加数据
     *
     * @param request
     * @param article
     * @return
     */
    @RequestMapping("/addArticle")
    @ResponseBody
    public Map<String, Object> addArticle(HttpServletRequest request,
                                          @ModelAttribute("article") Article article) {
        Map<String, Object> resultMap = null;
        try {
            if (article != null) {
                articleBiz.save(article);
                resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
            }
        } catch (Exception e) {
            logger.error("ArticleController--addArticle", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 跳转到更新页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateArticle")
    public String toUpdateArticle(HttpServletRequest request,
                                  @RequestParam(value = "id", required = true) Long id) {
        try {
            Article article = articleBiz.findById(id);
            List<ArticleType> articleTypeList = articleTypeBiz.findAll();
            request.setAttribute("article", article);
            request.setAttribute("articleTypeList", articleTypeList);
        } catch (Exception e) {
            logger.error("ArticleController--toUpdateArticle", e);
        }
        return toUpdateArticle;
    }

    /**
     * 更新文章信息
     * @param request
     * @param article
     * @return
     */
    @RequestMapping("/updateArticle")
    @ResponseBody
    public Map<String,Object> updateArticle(HttpServletRequest request,
                                                @ModelAttribute("article") Article article) {

        Map<String, Object> resultMap = null;
        try {
            articleBiz.update(article);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("ArticleController--updateArticle", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 删除文章信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/deleteArticle")
    @ResponseBody
    public Map<String, Object> deleteArticle(HttpServletRequest request,
                                                 @RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            if (id != null && id > 0) {
                articleBiz.deleteById(id);
                resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
            } else {
                return this.resultJson(ErrorCode.ERROR_DATA, "系统繁忙，请稍后重试", null);
            }
        } catch (Exception e) {
            logger.error("ArticleController--deleteArticle", e);
        }
        return resultMap;
    }
}
