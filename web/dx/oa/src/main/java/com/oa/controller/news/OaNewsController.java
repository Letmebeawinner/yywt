package com.oa.controller.news;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.reflect.TypeToken;
import com.oa.biz.common.MenHuHessianService;
import com.oa.biz.news.OaNewsBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.common.CommonConstants;
import com.oa.common.HttpUtils;
import com.oa.entity.article.Article;
import com.oa.entity.news.OaNews;
import com.oa.entity.sysuser.SysUser;
import com.oa.entity.workflow.OaLetter;
import org.apache.http.client.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻发布审批
 *
 * @author lzh
 * @create 2017-11-01-17:08
 */
@Controller
@RequestMapping("/admin/oa")
public class OaNewsController extends BaseController {
    
    private static Logger logger = LoggerFactory.getLogger(OaNewsController.class);

    private static final String delArtilce = CommonConstants.informationPath + "/article/del.json";

    @Autowired
    private OaNewsBiz oaNewsBiz;
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private MenHuHessianService menHuHessianService;

    @InitBinder("oaNews")
    public void initNews(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("oaNews.");
    }

    /**
     * @Description: 启动流程，将数据存入业务表，同时关联起来
     * @author: lzh
     * @Param: [request, news, processDefinitionId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 20:35
     */
    @RequestMapping("/oaNews/process/start")
    @ResponseBody
    public Map<String, Object> startProcess(HttpServletRequest request,
                                            @ModelAttribute("oaNews") OaNews news,
                                            @RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String, Object> json = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String processInstanceId = oaNewsBiz.tx_startNewsProcess(news, processDefinitionId, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaNewsController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/news/complete")
    @ResponseBody
    public Map<String, Object> completeTask(@RequestParam("taskId") String taskId,
                                            @RequestParam("flag") int flag,
                                            @RequestParam("processInstanceId") String processInstanceId,
                                            @RequestParam("comment") String comment,
                                            @ModelAttribute("oaNews") OaNews oaNews,
                                            HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            workflowFormBiz.tx_startAudit(taskId, processInstanceId, comment, flag, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("OaNewsController.completeTask", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 新闻审核
     * @author: lzh
     * @Param: [taskId, carApplyOpiont, refuseReason]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:31
     */
    @RequestMapping("/newsApplyAudit")
    @ResponseBody
    public Map<String, Object> newsApplyAudit(@RequestParam("taskId") String taskId,
                                              @ModelAttribute("oaNews") OaNews oaNews,
                                              @RequestParam(value = "comment", required = false) String comment,
                                              HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaNewsBiz.tx_startNewsApplyAudit(oaNews, taskId, comment, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaNewsController.newsApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    /**
//     * 发布新闻
//     * @param taskId
//     * @param userIds
//     * @param newsId
//     * @param request
//     * @return
//     */
//    @RequestMapping("/news/publish")
//    @ResponseBody
//    public Map<String, Object> newsPublish(@RequestParam("taskId") String taskId,
//                                             @RequestParam("userIds") String userIds,
//                                             @RequestParam("newsId") Long newsId,
//                                             HttpServletRequest request) {
//        Map<String, Object> json = null;
//        Long userId = SysUserUtils.getLoginSysUserId(request);
//        try {
//            oaNewsBiz.saveNewsPublish(taskId, userIds, newsId, userId);
//            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
//        } catch(Exception e) {
//            logger.error("OaNewsController.newsPublish", e);
//            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
//        }
//        return json;
//    }

    @RequestMapping("/sendNews")
    @ResponseBody
    public Map<String, Object> sendNews(@RequestParam("id") Long id,
                                        @RequestParam("outNetId") Long outerId) {
        OaNews oaNews = new OaNews();
        oaNews.setId(id);
        oaNews.setSendStatus(1);
        oaNews.setOuterId(outerId);
        Map<String, Object> json = null;
        try {
            oaNewsBiz.update(oaNews);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("OaNewsController.sendNews", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.ERROR_SYSTEM, null);
        }
        return json;
    }

    /**
     * 发送信息到外网
     * @return
     */
    @RequestMapping("/ajax/news/type")
    public String sendNewsToOuter() {
        return "/news/oa_news_select_type";
    }

    /**
     * 发送消息到内网
     * @param pagination
     * @param request
     * @return
     */
    @RequestMapping("/ajax/news/inner/type")
    public String innerArticleTypeList(@ModelAttribute("pagination")Pagination pagination,
                                  HttpServletRequest request) {
        try {
            pagination.setPageSize(10);
            Map<String, Object> articleTypeMap = menHuHessianService.getArticleType(pagination, null);
            Pagination _pagination = gson.fromJson(articleTypeMap.get("pagination").toString(), Pagination.class);
            pagination.setTotalCount(_pagination.getTotalCount());
            pagination.setPageSize(_pagination.getPageSize());
            pagination.setCurrentUrl(_pagination.getCurrentUrl());
            pagination.setBegin(_pagination.getBegin());
            pagination.setEnd(_pagination.getEnd());
            pagination.setCurrentPage(_pagination.getCurrentPage());
            pagination.setTotalPages(_pagination.getTotalPages());
            List<Map<String, String>> articleTypeList = gson.fromJson(articleTypeMap.get("articleTypeList").toString(), new TypeToken<List<HashMap<String, String>>>(){}.getType());
            request.setAttribute("typeList", articleTypeList);
            pagination.setCurrentUrl(request.getRequestURI());
        } catch(Exception e) {
            logger.error("OaNewsController.innerArticleTypeList", e);
            return this.setErrorPath(request, e);
        }

        return "/news/oa_news_select_inner_type";
    }

    @RequestMapping("/ajax/send/inner")
    @ResponseBody
    public Map<String, Object> sendToInner(@RequestParam("content") String content,
                                           @RequestParam("author") String author,
                                           @RequestParam("typeId") Long typeId,
                                           @RequestParam("title") String title,
                                           @RequestParam("newsId") Long newsId,
                                           @RequestParam("fileUrl") String fileUrl) {
        Map<String, Object> json = null;
        OaNews oaNews = new OaNews();
        oaNews.setId(newsId);
        try {
            Map<String, String> articleMap = new HashMap<>();
            articleMap.put("content", content);
            articleMap.put("author", author);
            articleMap.put("title", title);
            articleMap.put("fileUrl", fileUrl);
            articleMap.put("typeId", typeId + "");
            articleMap.put("source", "推送");
            Long innerId = 0L;
            Map<String, Object> map = menHuHessianService.addArticle(articleMap);
            if (map != null) {
                Map<String, String> jsonMap = gson.fromJson(map.get("article").toString(), new TypeToken<HashMap<String, String>>(){}.getType());
                if (jsonMap != null && jsonMap.get("id") != null ) {
                    innerId = Long.parseLong(jsonMap.get("id"));
                    oaNews.setInnerId(innerId);
                    oaNews.setSendStatusInner(1);
                    oaNewsBiz.update(oaNews);
                }
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, innerId);
        } catch(Exception e) {
            logger.error("OaNewsController.sendToInner", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 取消信息发布
     * @param type
     * @param newsId
     * @return
     */
    @RequestMapping("/ajax/article/del")
    @ResponseBody
    public Map<String, Object> delArticleById(@RequestParam("type") String type,
                                              @RequestParam("newsId") Long newsId) {
        Map<String, Object> json = null;
        OaNews oaNews = new OaNews();
        oaNews.setId(newsId);

        OaNews news = oaNewsBiz.findById(newsId);
        Long articleId = 0L;

        try {
            if ("INNER".equals(type)) {
                articleId = news.getInnerId();
                int i = menHuHessianService.delArticleById(articleId);
                if (i == 1) {
                    oaNews.setSendStatusInner(0);
                    oaNewsBiz.update(oaNews);
                }
            } else {
                articleId = news.getOuterId();
                Map<String, String> map = new HashMap<>();
                map.put("id", articleId + "");
                String result = HttpUtils.doPost(delArtilce, map);
                System.out.println(result);
                if (result != null) {
                    Map<String, Object> resultMap = gson.fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    if (resultMap.get("code").equals("0")) {
                        oaNews.setSendStatus(0);
                        oaNewsBiz.update(oaNews);
                    }
                }
            }

            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("OaNewsController.delArticleById", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    @RequestMapping("/ajax/updateNews")
    @ResponseBody
    public Map<String, Object> sendNews(@RequestParam("id") Long id,
                                        @RequestParam("content") String content) {
        OaNews oaNews = new OaNews();
        oaNews.setId(id);
        oaNews.setContent(content);
        Map<String, Object> json = null;
        try {
            oaNewsBiz.update(oaNews);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("OaNewsController.sendNews", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.ERROR_SYSTEM, null);
        }
        return json;
    }
}
