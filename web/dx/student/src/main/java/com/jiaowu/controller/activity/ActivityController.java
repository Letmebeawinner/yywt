package com.jiaowu.controller.activity;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.activity.ActivityBiz;
import com.jiaowu.biz.activityReply.ActivityReplyBiz;
import com.jiaowu.biz.advice.AdviceBiz;
import com.jiaowu.biz.adviceReply.AdviceReplyBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.praise.PraiseBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.activity.Activity;
import com.jiaowu.entity.activityReply.ActivityReply;
import com.jiaowu.entity.advice.Advice;
import com.jiaowu.entity.adviceReply.AdviceReply;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.praise.Praise;
import com.jiaowu.entity.user.User;
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
 * Created by 李帅雷 on 2017/8/24.
 */
@Controller
public class ActivityController extends BaseController {
    private static Logger logger = LoggerFactory
            .getLogger(ActivityController.class);
    @Autowired
    private ActivityBiz activityBiz;
    @Autowired
    private PraiseBiz praiseBiz;
    @Autowired
    private ActivityReplyBiz activityReplyBiz;
    @Autowired
    private CommonBiz commonBiz;
    @Autowired
    private AdviceBiz adviceBiz;
    @Autowired
    private AdviceReplyBiz adviceReplyBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private UserBiz userBiz;
    private static final String ADMIN_PREFIX = "/admin/jiaowu";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu";
    @InitBinder({ "activity" })
    public void initActivity(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("activity.");
    }
    @InitBinder({"activityReply"})
    public void initActivityReply(WebDataBinder binder){
        binder.setFieldDefaultPrefix("activityReply.");
    }
    @InitBinder({"advice"})
    public void initAdvice(WebDataBinder binder){
        binder.setFieldDefaultPrefix("advice.");
    }
    @InitBinder({"adviceReply"})
    public void initAdviceReply(WebDataBinder binder){
        binder.setFieldDefaultPrefix("adviceReply.");
    }
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 跳转到新建活动的页面
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/toCreateActivity")
    public  String toCreateActivity(){
        return "/admin/activity/createActivity";
    }

    /**
     * 新建活动
     * @param activity
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/createActivity")
    @ResponseBody
    public Map<String,Object> createActivity(HttpServletRequest request,Activity activity){
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if(userMap.get("userType").equals("3")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限新建活动",
                        null);
            }
            String errorInfo = validateActivity(activity);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo,
                        null);
            }
            activity.setCreateUserId(Long.parseLong(userMap.get("linkId")));
            activity.setCreateUserName(userMap.get("userName"));
            activity.setType(Integer.parseInt(userMap.get("userType")));
            activityBiz.save(activity);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 跳转到修改活动的页面
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/toUpdateActivity")
    public  String toUpdateActivity(HttpServletRequest request,Long id){
        try{
            Activity activity=activityBiz.findById(id);
            request.setAttribute("activity",activity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/activity/updateActivity";
    }

    /**
     * 修改活动
     * @param activity
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/updateActivity")
    @ResponseBody
    public Map<String,Object> updateActivity(HttpServletRequest request,Activity activity){
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if(!userMap.get("userType").equals("1")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限修改活动",
                        null);
            }
            String errorInfo = validateActivity(activity);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo,
                        null);
            }
            activityBiz.update(activity);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 活动列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     *
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/activityList")
    public String activityList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination){
        try{
            Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
            List<Activity> activityList=null;
            String whereSql=" status=1";
            if(userMap.get("userType").equals("2")){
                whereSql+=" and type=2 and createUserId="+userMap.get("linkId");
            }
            Activity activity=new Activity();
            String title=request.getParameter("title");
            if(!StringUtils.isTrimEmpty(title)){
                whereSql+=" and title like '%"+title+"%'";
                activity.setTitle(title);
            }

            pagination.setRequest(request);
            activityList = activityBiz.find(pagination,whereSql);
            request.setAttribute("activityList",activityList);
            request.setAttribute("pagination",pagination);
            request.setAttribute("activity",activity);
            request.setAttribute("user",SysUserUtils.getLoginSysUser(request));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/admin/activity/activity_list";
    }

    /**
     * 验证活动
     * @param activity
     * @return
     */
    public String validateActivity(Activity activity){
        if(StringUtils.isTrimEmpty(activity.getTitle())){
            return "标题不能为空";
        }
        if(StringUtils.isTrimEmpty(activity.getContent())){
            return "内容不能为空";
        }
        return null;
    }

    /**
     * @Description 供选择的活动列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     *
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/activity/activityListForSelect")
    public String activityListForSelect(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination) {
        try {
            StringBuffer sb = request.getRequestURL().append(
                    "?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " status=1";
            Activity activity = new Activity();
            whereSql += activityBiz.addCondition(request, activity);
            pagination.setCurrentUrl(sb.toString());
            List<Activity> activityList = activityBiz.find(pagination, whereSql);
            request.setAttribute("activityList", activityList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("activity", activity);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/activity/activity_list_forSelect";
    }

    /**
     * @Description 删除活动
     * @param id
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX + "/activity/deleteActivity")
    @ResponseBody
    public Map<String, Object> deleteActivity(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Activity activity = new Activity();
            activity.setId(id);
            activity.setStatus(0);
            activityBiz.update(activity);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 跳转到活动详情页面
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     *
     */
    @RequestMapping(ADMIN_PREFIX + "/activity/detailOfOneActivity")
    public String detailOfOneActivity(HttpServletRequest request,
                                   @RequestParam("id") Long id) {
        try {
            Activity activity = activityBiz.findById(id);
            request.setAttribute("activity", activity);
            List<ActivityReply> activityReplyList = activityReplyBiz.find(null,
                    " status=1 and replyId=0 and activityId=" + id);
            request.setAttribute("activityReplyList", activityReplyList);
            activityBiz.addActivityViewNum(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/activity/one_activity";
    }

    /**
     * @Description 新增活动的回复
     * @param request
     * @param activityId
     * @param content
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX + "/activityreply/addReplyToActivity")
    @ResponseBody
    public Map<String, Object> addReplyToActivity(HttpServletRequest request,
                                               @RequestParam("activityId") Long activityId,
                                               @RequestParam("content") String content) {
        Map<String, Object> json = null;
        try {
            ActivityReply activityReply = new ActivityReply();
            json = validateActivityReply(request, activityReply, content);
            if (json != null) {
                return json;
            }
            activityReply.setActivityId(activityId);
            activityReply.setContent(content);
//			reply.setPraiseNum(0L);
//			reply.setReplyNum(0L);
            activityReplyBiz.save(activityReply);
            activityBiz.addActivityReplyNum(activityId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    activityReply);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 点赞
     * @param request
     * @param replyId
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX + "/activityreply/addPraise")
    @ResponseBody
    public Map<String, Object> addPraise(HttpServletRequest request,
                                         @RequestParam("replyId") Long replyId) {
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap == null) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                        "请先登录", null);
                return json;
            }
            Integer userType = Integer.parseInt(userMap.get("userType"));
            Long userId = Long.parseLong(userMap.get("linkId"));
            json = checkUserHasPraiseOneReply(userType, userId, replyId);
            if (json != null) {
                return json;
            }
            praiseBiz.save(userType, userId, replyId,2);
            Long praiseNum = activityReplyBiz.addReplyPraiseNum(replyId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    praiseNum);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }



    /**
     * @Description 获取某条回复的子回复
     * @param replyId
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX + "/activityreply/getChildReply")
    @ResponseBody
    public Map<String, Object> getChildReply(
            @RequestParam("replyId") Long replyId) {
        Map<String, Object> json = null;
        try {
            List<ActivityReply> activityReplyList = activityReplyBiz.find(null,
                    " status=1 and replyId=" + replyId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    activityReplyList);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 增加某条回复的子回复
     * @param request
     * @param replyId
     * @param content
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX + "/activityreply/addChildReply")
    @ResponseBody
    public Map<String, Object> addChildReply(HttpServletRequest request,
                                             @RequestParam("activityId") Long activityId,
                                             @RequestParam("replyId") Long replyId,
                                             @RequestParam("content") String content) {
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap == null) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                        "请先登录", null);
                return json;
            }
            json=validateReply(content);
            if(json!=null){
                return json;
            }
            ActivityReply activityReply = new ActivityReply();
            activityReply.setActivityId(activityId);
            activityReply.setUserId(Long.parseLong(userMap.get("linkId")));
            activityReply.setUserName(commonBiz.getCurrentUserName(request));
            activityReply.setType(Integer.parseInt(userMap.get("userType")));
            activityReply.setReplyId(replyId);
            activityReply.setContent(content);
            activityReplyBiz.save(activityReply);
            Long replyNum=activityReplyBiz.addReplyChildReplyNum(replyId);
            // 只是在页面中展示,无其它作用.因为子回复没有回复.
            activityReply.setReplyNum(replyNum);
            activityBiz.addActivityReplyNum(activityId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    activityReply);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }



    /**
     * @Description 回复列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     *
     */
    @RequestMapping(ADMIN_PREFIX + "/activityreply/replyList")
    public String replyList(HttpServletRequest request,
                            @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " status=1";
            Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
            if(userMap.get("userType").equals("2")){
                List<Activity> activityList=activityBiz.find(null," type=2 and createUserId="+userMap.get("linkId"));
                if(activityList!=null&&activityList.size()>0){
                    StringBuffer sb=new StringBuffer();
                    for(Activity activity:activityList){
                        sb.append(activity.getId()+",");
                    }
                    whereSql+=" and activityId in ("+sb.substring(0,sb.length()-1)+")";
                }else{
                    whereSql+=" and activityId=0";
                }

            }
            ActivityReply activityReply= new ActivityReply();
            whereSql+=activityReplyBiz.addCondition(request, activityReply);
            pagination.setRequest(request);
            List<ActivityReply> activityReplyList = activityReplyBiz.find(pagination, whereSql);
            activityBiz.setActivityTitle(activityReplyList);
            request.setAttribute("activityReplyList", activityReplyList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("activityReply", activityReply);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/activity/reply_list";
    }

    /**
     * @Description 删除回复
     * @param id
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX + "/activityreply/deleteReply")
    @ResponseBody
    public Map<String, Object> deleteReply(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            ActivityReply activityReply = new ActivityReply();
            activityReply.setId(id);
            activityReply.setStatus(0);
            activityReplyBiz.update(activityReply);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证活动
     *
     * @param request
     * @return
     */
    public Map<String, Object> validateActivity(HttpServletRequest request,
                                             Activity activity) {
        Map<String, Object> error = null;
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        if (userMap != null) {
            activity.setCreateUserId(Long.parseLong(userMap.get("linkId")));
            activity.setCreateUserName(commonBiz.getCurrentUserName(request));
        } else {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请先登录",
                    null);
            return error;
        }
        if (StringUtils.isTrimEmpty(activity.getTitle())) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空",
                    null);
            return error;
        }
        if (StringUtils.isTrimEmpty(activity.getContent())) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空",
                    null);
            return error;
        }
        if (activity.getTitle().length() > 50) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称过长",
                    null);
            return error;
        }
        if (activity.getContent().length() > 10000) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容过长",
                    null);
            return error;
        }
        return error;
    }

    /**
     * 验证回复
     *
     * @param request
     * @param content
     * @return
     */
    public Map<String, Object> validateActivityReply(HttpServletRequest request,
                                             ActivityReply activityReply, String content) {
        Map<String, Object> error = null;
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        if (userMap != null) {
            activityReply.setUserId(Long.parseLong(userMap.get("linkId")));
            activityReply.setUserName(commonBiz.getCurrentUserName(request));
            activityReply.setType(Integer.parseInt(userMap.get("userType")));
        } else {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请先登录",
                    null);
            return error;
        }
        if (StringUtils.isTrimEmpty(content)) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请填写内容",
                    null);
            return error;
        }
        if (content.length() > 1000) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容过长",
                    null);
            return error;
        }
        return error;
    }

    /**
     * 查询用户是否已对某条回复点赞
     * @param userType
     * @param userId
     * @param replyId
     * @return
     */
    public Map<String, Object> checkUserHasPraiseOneReply(Integer userType,
                                                          Long userId, Long replyId) {
        List<Praise> praiseList = praiseBiz.find(null, " status=1 and replyId="
                + replyId + " and userId=" + userId + " and type=" + userType+" and belong=2");
        if (praiseList != null && praiseList.size() > 0) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您已赞过",
                    null);
        }
        return null;
    }

    /**
     * 验证回复
     * @param content
     * @return
     */
    public Map<String,Object> validateReply(String content){
        Map<String,Object> error=null;
        if (StringUtils.isTrimEmpty(content)) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "请填写内容", null);
            return error;
        }
        if (content.length() > 1000) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "内容过长", null);
            return error;
        }
        return null;
    }

    /**
     * 检查当前登录用户是否可以添加建议
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/checkUserCanCreateAdvice")
    @ResponseBody
    public Map<String,Object> checkUserCanCreateAdvice(HttpServletRequest request,Long id){
        Map<String,Object> json=null;
        try{
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if(!userMap.get("userType").equals("3")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限建议活动",
                        null);
            }

            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 跳转到添加建议的页面
     * @param activityId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/toCreateAdvice")
    public String toCreateAdvice(HttpServletRequest request,Long activityId){
        try{
            Activity activity=activityBiz.findById(activityId);
            request.setAttribute("activity",activity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/activity/createAdvice";
    }

    /**
     * 添加建议
     * @param advice
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/createAdvice")
    @ResponseBody
    public Map<String,Object> createAdvice(HttpServletRequest request,Advice advice){
        Map<String,Object> json=null;
        try{
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if(!userMap.get("userType").equals("3")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限建议活动",
                        null);
            }
            if(StringUtils.isTrimEmpty(advice.getContent())){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请填写内容",
                        null);
            }
            Activity activity=activityBiz.findById(advice.getActivityId());
            advice.setActivityTitle(activity.getTitle());
            advice.setCreateUserId(Long.parseLong(userMap.get("linkId")));
            User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
            advice.setClassId(user.getClassId());
            advice.setCreateUserName(userMap.get("userName"));
            advice.setType(3);
            adviceBiz.save(advice);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 跳转到回复建议的页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/toAddAdviceReply")
    public String toAddAdviceReply(HttpServletRequest request,Long id){
        try{
            Advice advice=adviceBiz.findById(id);
            request.setAttribute("advice",advice);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/activity/addAdviceReply";
    }

    /**
     * 回复建议
     * @param request
     * @param adviceReply
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/addAdviceReply")
    @ResponseBody
    public Map<String,Object> addAdviceReply(HttpServletRequest request,AdviceReply adviceReply){
        Map<String,Object> json=null;
        try{
            if(StringUtils.isTrimEmpty(adviceReply.getContent())){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请填写内容",
                        null);
            }
            Advice advice=new Advice();
            advice.setId(adviceReply.getAdviceId());
            advice.setHasReply(1);
            adviceBiz.update(advice);

            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            adviceReply.setCreateUserId(Long.parseLong(userMap.get("linkId")));
            adviceReply.setCreateUserName(userMap.get("userName"));
            adviceReply.setType(Integer.parseInt(userMap.get("userType")));
            adviceReplyBiz.save(adviceReply);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        }catch (Exception e){
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 建议列表
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/activity/adviceList")
    public String adviceList(HttpServletRequest request,
                             @ModelAttribute("pagination") Pagination pagination){
        try{

            List<Advice> adviceList=null;
            String whereSql=" status=1";
            Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
            if(userMap.get("userType").equals("2")){
                List<Classes> classesList=classesBiz.find(null," status=1 and teacherId="+userMap.get("linkId"));
                if(classesList!=null&&classesList.size()>0){
                    whereSql+=" and classId="+classesList.get(0).getId();
                }else{
                    whereSql+=" and classId=0";
                }
            }
            /*Advice advice=new Advice();
            String title=request.getParameter("title");
            if(!StringUtils.isTrimEmpty(title)){
                whereSql+=" and title like '%"+title+"%'";
                advice.setTitle(title);
            }*/
            pagination.setRequest(request);
            adviceList = adviceBiz.find(pagination,whereSql);
            request.setAttribute("adviceList",adviceList);
            request.setAttribute("pagination",pagination);
//            request.setAttribute("activity",activity);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/admin/activity/advice_list";
    }
}
