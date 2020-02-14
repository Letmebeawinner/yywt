package com.oa.controller.news;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.news.NewsBiz;
import com.oa.biz.news.NewsDepartmentBiz;
import com.oa.biz.news.NewsTypeBiz;
import com.oa.dao.news.NewsDao;
import com.oa.entity.department.DepartMent;
import com.oa.entity.news.*;
import com.oa.entity.sysuser.SysUser;
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
 * 新闻控制层
 *
 * @author lzh
 * @create 2016-12-28-17:33
 */
@Controller
@RequestMapping("/admin/oa")
public class NewsController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsDao newsDaos;
    @Autowired
    private NewsBiz newsBiz;
    @Autowired
    private NewsTypeBiz newsTypeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private NewsDepartmentBiz newsDepartmentBiz;


    private static final String newsList = "/news/news_list";
    private static final String newsStatistic = "/news/news_statistic";
    private static final String toAddNews = "/news/news_add";
    private static final String toUpdateNews = "/news/news_update";
    private static final String toPublishNews = "/news/news_publish";

    /**
     * 未勾选
     */
    private static final int NO_OPTION = 0;

    @InitBinder("news")
    public void initBinderNews(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("news.");
    }

    /**
     * @Description:查询所有新闻
     * @author: lzh
     * @Param: request, pagination
     * @Return: String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllNews")
    public String queryAllNews(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("news") News news) {
        try {
            pagination.setRequest(request);
            List<NewsDto> newsDtos = newsBiz.getNewsDtos(pagination, news);
            List<NewsType> newsTypes = newsTypeBiz.findAll();
            request.setAttribute("news", news);
            request.setAttribute("newsDtos", newsDtos);
            request.setAttribute("newsTypes", newsTypes);
        } catch (Exception e) {
            logger.error("NewsController.queryAllNews", e);
            return this.setErrorPath(request, e);
        }
        return newsList;
    }

    /**
     * @Description:到增加新闻页
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddNews")
    public String toAddNews(HttpServletRequest request) {
        try {
            List<NewsType> newsTypes = newsTypeBiz.findAll();
            request.setAttribute("newsTypes", newsTypes);
        } catch (Exception e) {
            logger.error("NewsController.toAddNews", e);
            return this.setErrorPath(request, e);
        }
        return toAddNews;
    }

    /**
     * @Description: 到更新新闻页
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdateNews")
    public String toUpdateNews(HttpServletRequest request, @RequestParam("id") Long id, @RequestParam(value = "flag", required = false) int flag) {
        try {
            News news = newsBiz.findById(id);
            List<NewsType> newsTypes = newsTypeBiz.findAll();
            request.setAttribute("newsTypes", newsTypes);
            request.setAttribute("flag", flag);
            request.setAttribute("news", news);
        } catch (Exception e) {
            logger.error("NewsController.toUpdateNews", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateNews;
    }

    /**
     * @Description: 增加新闻
     * @author: lzh
     * @Param: [news]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addNews")
    @ResponseBody
    public Map<String, Object> addNews(@ModelAttribute("news") News news) {
        Map<String, Object> resultMap;
        try {
            // 判断属性 热点/推荐
            if (ObjectUtils.isNull(news.getHot())) {
                // 没有勾选热点时
                news.setHot(NO_OPTION);
            }
            if (ObjectUtils.isNull(news.getRecommend())) {
                // 没有勾选推荐时
                news.setRecommend(NO_OPTION);
            }
            newsBiz.save(news);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("NewsController.addNews", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description: 更新新闻
     * @author: lzh
     * @Param: [news]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updateNews")
    @ResponseBody
    public Map<String, Object> updateNews(@ModelAttribute("news") News news, HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            // 判断属性 热点/推荐
            if (ObjectUtils.isNull(news.getHot())) {
                // 没有勾选热点时
                news.setHot(NO_OPTION);
            }
            if (ObjectUtils.isNull(news.getRecommend())) {
                // 没有勾选推荐时
                news.setRecommend(NO_OPTION);
            }
            newsBiz.update(news);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("NewsController.updateNews", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description: 删除新闻
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deleteNews")
    @ResponseBody
    public Map<String, Object> deleteNews(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            newsBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("NewsController.deleteNews", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }

    /**
     * @Description:到发布新闻
     * @author: lzh
     * @Param: [id, request]
     * @Return: java.lang.String
     * @Date: 10:55
     */
    @RequestMapping("/toPublishNews")
    public String toPublishNews(@RequestParam("id") Long id, HttpServletRequest request) {
        try {
            DepartMent departMent = new DepartMent();
            List<DepartMent> departments = baseHessianBiz.getDepartMentList(departMent);
            NewsDepartment newsDepartment = new NewsDepartment();
            newsDepartment.setNewsId(id);
            List<NewsDepartment> newsDepartments = newsDepartmentBiz.find(null, GenerateSqlUtil.getSql(newsDepartment));
            News news = newsBiz.findById(id);
            request.setAttribute("newsDepartments", gson.toJson(newsDepartments));
            request.setAttribute("departments", gson.toJson(departments));
            request.setAttribute("news", news);
        } catch (Exception e) {
            logger.error("NewsController.publishNews", e);
            return this.setErrorPath(request, e);
        }
        return toPublishNews;
    }

    /**
     * @Description: 发布新闻
     * @author: lzh
     * @Param: [id, ids] id 新闻id，ids部门ids
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 11:09
     */
    @RequestMapping("/publishNews")
    @ResponseBody
    public Map<String, Object> publishNews(@RequestParam("id") Long id, @RequestParam("ids[]") Long[] ids, HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            newsBiz.tx_publish(userId, id, ids);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "发布成功", null);
        } catch (Exception e) {
            logger.error("NewsController.publishNews", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 统计新闻总量
     *
     * @return
     */
    @RequestMapping("/statisticNews")
    public String statisticNews(HttpServletRequest request) {
        try {
            List<OaNewsDto> newsList = newsDaos.queryOaNewsStatistic();
            request.setAttribute("newsList", newsList);

            if(ObjectUtils.isNotNull(newsList)){
                for(OaNewsDto n : newsList){
                    SysUser sysUser = baseHessianBiz.getSysUserById(n.getApplyId());
                    String userName = "";
                    if (sysUser != null) {
                        userName = sysUser.getUserName();
                    }
                    n.setUserName(userName);
                }
            }

            String xAxis="";
            if(ObjectUtils.isNotNull(newsList)){
                for(OaNewsDto n:newsList){
                   xAxis+="\""+n.getUserName()+"\""+",";
                }
                xAxis=xAxis.substring(0,xAxis.length()-1);
            }

            String yAxis="";
            if(ObjectUtils.isNotNull(newsList)){
                for(OaNewsDto n:newsList){
                    yAxis+=n.getNum()+",";
                }
                yAxis=yAxis.substring(0,yAxis.length()-1);
            }

            request.setAttribute("xAxis",xAxis);
            request.setAttribute("yAxis",yAxis);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsStatistic;
    }


}
