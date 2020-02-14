package com.oa.controller.article;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.article.ArticleTypeBiz;
import com.oa.entity.article.ArticleType;
import com.oa.utils.GenerateSqlUtil;
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
 * 文章类型控制层
 *
 * @author wanghailong
 * @create 2017-04-12-下午 3:15
 */
@Controller
@RequestMapping("/admin/oa")
public class ArticleTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ArticleTypeController.class);

    @Autowired
    private ArticleTypeBiz articleTypeBiz;

    private static final String toAddArticleType = "/article/articleType_add";//添加文章类型
    private static final String toUpdateArticleType = "/article/articleType_update";//修改文章类型
    private static final String ArticleTypeList = "/article/articleType_list";//文章类型列表

    @InitBinder("articleType")
    public void initBinderArticle(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.setFieldDefaultPrefix("articleType.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true, 10));
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dateFormat, true, 19));
    }

    /**
     * 获得全部文章类型
     *
     * @param request
     * @param pagination
     * @param articleType
     * @return
     */
    @RequestMapping("/queryAllArticleType")
    public String queryAllArticleType(HttpServletRequest request,
                                      @ModelAttribute("pagination") Pagination pagination,
                                      @ModelAttribute("articleType") ArticleType articleType) {
        pagination.setPageSize(10);
        pagination.setRequest(request);
        try {
            request.setAttribute("pagination", pagination);
            String whereSql = GenerateSqlUtil.getSql(articleType);
            List<ArticleType> articleTypeList = articleTypeBiz.find(pagination, whereSql);
            request.setAttribute("articleTypeList", articleTypeList);
        } catch (Exception e) {
            logger.error("ArticleTypeController--queryAllArticleType", e);
        }
        return ArticleTypeList;
    }

    /**
     * 跳转到文章类型添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddArticleType")
    public String toAddArticleType(HttpServletRequest request) {
        return toAddArticleType;
    }

    /**
     * 添加文章类型
     *
     * @param request
     * @param articleType
     * @return
     */
    @RequestMapping("/addArticleType")
    @ResponseBody
    public Map<String, Object> addArticleType(HttpServletRequest request,
                                              @ModelAttribute("articleType") ArticleType articleType) {
        Map<String, Object> resultMap = null;
        try {
            if (articleType != null) {
                articleTypeBiz.save(articleType);
                resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
            }
        } catch (Exception e) {
            logger.error("ArticleTypeController--addArticleType", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 去更新数据
     *
     * @param request
     * @param pagination
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateArticleType")
    public String toUpdateArticleType(HttpServletRequest request,
                                      @ModelAttribute("pagination") Pagination pagination,
                                      @RequestParam(value = "id", required = true) Long id) {

        try {
            ArticleType articleType=articleTypeBiz.findById(id);
            request.setAttribute("articleType", articleType);
        } catch (Exception e) {
            logger.error("ArticleTypeController--toUpdateArticleType", e);
        }
        return toUpdateArticleType;
    }

    /**
     * 更新信息
     * @param request
     * @param articleType
     * @return
     */
    @RequestMapping("/updateArticleType")
    @ResponseBody
    public Map<String,Object> updateArticleType(HttpServletRequest request,
                                    @ModelAttribute("articleType") ArticleType articleType) {

        Map<String, Object> resultMap = null;
        try {
            articleTypeBiz.update(articleType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("ArticleTypeController--updateArticleType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 删除文章类型
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/deleteArticleType")
    @ResponseBody
    public Map<String, Object> deleteArticleType(HttpServletRequest request,
                                                 @RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            if (id != null && id > 0) {
                articleTypeBiz.deleteById(id);
                resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
            } else {
                return this.resultJson(ErrorCode.ERROR_DATA, "系统繁忙，请稍后重试", null);
            }
        } catch (Exception e) {
            logger.error("ArticleTypeController--deleteArticleType", e);
        }
        return resultMap;
    }
}
