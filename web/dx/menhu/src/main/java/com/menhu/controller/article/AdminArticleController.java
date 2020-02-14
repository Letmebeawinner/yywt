package com.menhu.controller.article;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.common.reflect.TypeToken;
import com.menhu.biz.article.ArticleBiz;
import com.menhu.biz.article.ArticleTypeBiz;
import com.menhu.entity.article.Article;
import com.menhu.entity.article.ArticleType;
import com.menhu.enums.Status;
import com.menhu.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@RequestMapping("/admin/menhu/article")
public class AdminArticleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AdminArticleController.class);

    private static final String article_add = "admin/article/article_add";
    private static final String article_list = "admin/article/article_list";
    private static final String article_update = "admin/article/article_update";

    @Resource
    private ArticleBiz articleBiz;
    @Resource
    private ArticleTypeBiz articleTypeBiz;

    @InitBinder({"article"})
    public void initArticle(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("article.");
    }


    /**
     * @Description: 跳转添加页面
     */
    @RequestMapping("/toAddArticle")
    public String toAddArticle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String whereSql = "status=" + Status.正常数据.getStatus();
            List<ArticleType> articleTypeList = articleTypeBiz.find(null, whereSql);
            request.setAttribute("articleTypeList", articleTypeList);
        } catch (Exception e) {
            logger.info("ArticleController--toAddArticle", e);
            return this.setErrorPath(request, e);
        }
        return article_add;
    }


    /**
     * @Description: 添加资讯
     */
    @RequestMapping("/addArticle")
    @ResponseBody
    public Map<String, Object> addArticle(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("article") Article article) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);//获得当前登录的用户id
            article.setStatus(0);
            article.setUserId(userId);
            List<String> pictureList = gson.fromJson(article.getPicture(), new TypeToken<List<String>>() {
            }.getType());
            if (ObjectUtils.isNotNull(pictureList)) {
                if (pictureList.size() > 3) {
                    List<String> list = new ArrayList<>();
                    list.add(pictureList.get(0));
                    list.add(pictureList.get(1));
                    list.add(pictureList.get(2));
                    article.setPicture(gson.toJson(list));
                }
            }
            articleBiz.save(article);
        } catch (Exception e) {
            logger.info("ArticleController-addArticle", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "/admin/menhu/article/index.json");
    }

    /**
     * @Description: 资讯查询
     */
    @RequestMapping("/index")
    public String queryArticlePage(HttpServletRequest request, HttpServletResponse response,
                                   @ModelAttribute("pagination") Pagination pagination,
                                   @ModelAttribute("article") Article article) {
        try {
            pagination.setRequest(request);
            pagination.setPageSize(10);
            //查询文章
            String whereSql = GenerateSqlUtil.getSql(article);
            whereSql += " and status=" + Status.正常数据.getStatus();
            whereSql += " order by id desc";
            List<Article> articleList = articleBiz.find(pagination, whereSql);
            request.setAttribute("articleList", articleList);

            //类型列表
            List<ArticleType> articleTypeList = articleTypeBiz.find(null, "1=1");
            request.setAttribute("articleTypeList", articleTypeList);

        } catch (Exception e) {
            logger.info("ArticleController-queryArticlePage", e);
            return this.setErrorPath(request, e);
        }
        return article_list;
    }


    /**
     * @Description: 跳转到修改页面
     */
    @RequestMapping("/toUpdateArticle")
    public String toUpdateArticle(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long id) {
        try {
            String whereSql = "status=" + Status.正常数据.getStatus();
            List<ArticleType> articleTypeList = articleTypeBiz.find(null, whereSql);
            Article article = articleBiz.findById(id);
            request.setAttribute("articleTypeList", articleTypeList);
            request.setAttribute("article", article);
            List<String> listPicture = gson.fromJson(article.getPicture(), new TypeToken<List<String>>() {
            }.getType());
            request.setAttribute("pictureSplit", listPicture);
        } catch (Exception e) {
            logger.info("ArticleController--toUpdateArticle", e);
            return this.setErrorPath(request, e);
        }
        return article_update;
    }

    /**
     * @Description:修改资讯
     */
    @RequestMapping("/updateArticle")
    @ResponseBody
    public Map<String, Object> updateArticle(HttpServletRequest request, @ModelAttribute("article") Article article) {
        try {
            List<String> pictureList = gson.fromJson(article.getPicture(), new TypeToken<List<String>>() {
            }.getType());
            if (ObjectUtils.isNotNull(pictureList)) {
                if (pictureList.size() > 3) {
                    List<String> list = new ArrayList<>();
                    list.add(pictureList.get(0));
                    list.add(pictureList.get(1));
                    list.add(pictureList.get(2));
                    article.setPicture(gson.toJson(list));
                }
            }
            articleBiz.update(article);
        } catch (Exception e) {
            logger.info("ArticleController--updateArticle", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "/admin/menhu/article/index.json");
    }

    /**
     * @Description: 根据id修改资讯状态
     */
    @RequestMapping("/updStatus")
    @ResponseBody
    public Map<String, Object> updStatus(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long id, @RequestParam("status") int status) {
        try {
            if (Status.正常数据.getStatus() != status) {
                status = Status.删除数据.getStatus();
            }
            Article article = articleBiz.findById(id);
            article.setStatus(status);
            articleBiz.update(article);
        } catch (Exception e) {
            logger.info("ArticleController--updStatus", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "/admin/menhu/article/index.json");
    }


    /**
     * @Description: 根据类型ID 查询资讯
     */
    @RequestMapping("/queryArticleByTypeId")
    @ResponseBody
    public Map<String, Object> queryArticleByTypeId(HttpServletRequest request, HttpServletResponse response, @RequestParam("typeId") Long typeId) {
        try {
            String whereSql = " Status=" + Status.正常数据.getStatus() + " and typeId=" + typeId;
            List<Article> articleList = articleBiz.find(null, whereSql);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, articleList);
        } catch (Exception e) {
            logger.info("ArticleController.java--queryArticleByTypeId", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 删除资讯
     *
     * @return
     */
    @RequestMapping("/deleteArticleById")
    @ResponseBody
    public Map<String, Object> deleteArticleById(@RequestParam("id") Long id) {
        try {
            articleBiz.deleteById(id);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
    }
}


