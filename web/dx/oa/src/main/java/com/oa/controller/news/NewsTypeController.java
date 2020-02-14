package com.oa.controller.news;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.news.NewsTypeBiz;
import com.oa.entity.news.NewsType;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 新闻类型
 * @author lzh
 * @create 2016/12/27
 */
@Controller
@RequestMapping("/admin/oa")
public class NewsTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NewsTypeController.class);

    @Autowired
    private NewsTypeBiz newsTypeBiz;

    private static final String newsTypeList = "/news/news_type_list";
    private static final String toAddNewsType = "/news/news_type_add";
    private static final String toUpdateNewsType = "/news/news_type_update";

    @InitBinder("newsType")
    public void initBinderNewsType(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("newsType.");
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: request,pagination
     * @Return:  String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllNewsType")
    public String queryAllMeeting(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("newsType") NewsType newsType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(newsType);
            pagination.setRequest(request);
            List<NewsType> newsTypes = newsTypeBiz.find(pagination, whereSql);
            request.setAttribute("newsTypes", newsTypes);
        } catch (Exception e) {
            logger.error("NewsTypeController.queryAllNewsType", e);
            return this.setErrorPath(request, e);
        }
        return newsTypeList;
    }

    /**
     * 查询所有新闻类型
     * @return
     */
    @RequestMapping("/ajax/queryAllNewsType")
    @ResponseBody
    public Map<String, Object> ajaxQueryAllNewsType() {
        Map<String, Object> json = null;
        try {
            List<NewsType> newsTypes = newsTypeBiz.findAll();
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, newsTypes);
        } catch (Exception e) {
            logger.error("NewsTypeController.ajaxQueryAllNewsType", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 根据类型id查询新闻类型名
     * @return
     */
    @RequestMapping("/ajax/queryNewsType")
    @ResponseBody
    public Map<String, Object> ajaxQueryNewsTypeById(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            NewsType newsType = newsTypeBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, newsType);
        } catch (Exception e) {
            logger.error("NewsTypeController.ajaxQueryNewsTypeById", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddNewsType")
    public String toAddNewsType() {
        return toAddNewsType;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdateNewsType")
    public String toUpdateNewsType(HttpServletRequest request, @RequestParam("id") Long id,  @RequestParam(value = "flag", required = false) int flag) {
        try{
            NewsType newsType = newsTypeBiz.findById(id);
            request.setAttribute("newsType", newsType);
            request.setAttribute("flag", flag);
        } catch(Exception e) {
            logger.error("NewsTypeController.toUpdateNewsType", e);
            this.setErrorPath(request, e);
        }
        return toUpdateNewsType;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [newsType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addNewsType")
    @ResponseBody
    public Map<String, Object> addNewsType(@ModelAttribute("newsType") NewsType newsType) {
        Map<String,Object> resultMap = null;
        try {
            newsTypeBiz.save(newsType);
            resultMap=this.resultJson(ErrorCode.SUCCESS,"添加成功", null);
        } catch (Exception e) {
            logger.error("NewsTypeController.addNewsType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [newsType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updateNewsType")
    @ResponseBody
    public Map<String, Object> updateNewsType(@ModelAttribute("newsType") NewsType newsType) {
        Map<String, Object> resultMap = null;
        try {
            newsTypeBiz.update(newsType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("NewsTypeController.updateNewsType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deleteNewsType")
    @ResponseBody
    public Map<String, Object> delNewsType(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            newsTypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("NewsTypeController.delNewsType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }

}
