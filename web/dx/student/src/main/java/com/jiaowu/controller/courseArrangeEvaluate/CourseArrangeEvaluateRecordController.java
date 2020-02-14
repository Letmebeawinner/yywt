package com.jiaowu.controller.courseArrangeEvaluate;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.courseArrangeEvaluate.CourseArrangeEvaluateBiz;
import com.jiaowu.biz.courseArrangeEvaluateRecord.CourseArrangeEvaluateRecordBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.courseArrangeEvaluate.CourseArrangeEvaluate;
import com.jiaowu.entity.courseArrangeEvaluateRecord.CourseArrangeEvaluateRecord;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/10/17.
 */
@Controller
public class CourseArrangeEvaluateRecordController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(CourseArrangeEvaluateRecordController.class);
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }
    @InitBinder({"courseArrangeEvaluate"})
    public void initCourseArrangeEvaluate(WebDataBinder binder){
        binder.setFieldDefaultPrefix("courseArrangeEvaluate.");
    }
    @InitBinder({"courseArrangeEvaluateRecord"})
    public void initCourseArrangeEvaluateRecord(WebDataBinder binder){
        binder.setFieldDefaultPrefix("courseArrangeEvaluateRecord.");
    }

    @Autowired
    private CourseArrangeEvaluateRecordBiz courseArrangeEvaluateRecordBiz;
    @Autowired
    private CourseArrangeEvaluateBiz courseArrangeEvaluateBiz;
    @Autowired
    private UserBiz userBiz;

    private static final String ADMIN_PREFIX="/admin/student/courseArrangeEvaluate";

    /**
     * 我未完成的课后评价
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/myCourseArrangeEvaluate")
    public String myCourseArrangeEvaluate(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
        try{
            pagination.setRequest(request);
            Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);
            Long userId=Long.parseLong(userMap.get("linkId"));
            User user=userBiz.findById(userId);
            List<CourseArrangeEvaluate> courseArrangeEvaluateList=courseArrangeEvaluateBiz.find(pagination," classId="+user.getClassId()+" and id not in (select courseArrangeEvaluateId from coursearrangeevaluaterecord where userId="+userId+")");
            request.setAttribute("courseArrangeEvaluateList",courseArrangeEvaluateList);
            request.setAttribute("pagination",pagination);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/courseArrangeEvaluate/myCourseArrangeEvaluateList";
    }

    /**
     * 已完成课后评价列表
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/courseArrangeEvaluateRecordList")
    public String courseArrangeEvaluateRecordList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
        try{
            String whereSql=" status=1";
            Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
            if(userMap.get("userType").equals("1")){

            }else if(userMap.get("userType").equals("2")){
                //应获取该班次下学员的意见反馈
                return "/admin/courseArrangeEvaluate/courseArrangeEvaluateRecordList";
            }else if(userMap.get("userType").equals("3")){
                whereSql+=" and userId="+userMap.get("linkId");
            }

            pagination.setRequest(request);
            List<CourseArrangeEvaluateRecord> courseArrangeEvaluateRecordList = courseArrangeEvaluateRecordBiz.find(pagination,whereSql);
            request.setAttribute("courseArrangeEvaluateRecordList",courseArrangeEvaluateRecordList);
            request.setAttribute("pagination",pagination);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/courseArrangeEvaluate/courseArrangeEvaluateRecordList";
    }

    /**
     * 跳转到填写课后评价的页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/toWriteCourseArrangeEvaluate")
    public String toWriteCourseArrangeEvaluate(HttpServletRequest request,Long id){
        try{
            CourseArrangeEvaluate courseArrangeEvaluate=courseArrangeEvaluateBiz.findById(id);
            request.setAttribute("courseArrangeEvaluate",courseArrangeEvaluate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/courseArrangeEvaluate/writeCourseArrangeEvaluate";
    }

    /**
     * 填写课后评价
     * @param courseArrangeEvaluateRecord
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/writeCourseArrangeEvaluate")
    @ResponseBody
    public Map<String,Object> writeCourseArrangeEvaluate(HttpServletRequest request,CourseArrangeEvaluateRecord courseArrangeEvaluateRecord){
        Map<String,Object> json=null;
        try{
            if(StringUtils.isTrimEmpty(courseArrangeEvaluateRecord.getContent())){
                json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请填写评价内容", null);
                return json;
            }
            Long userId=Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            User user=userBiz.findById(userId);
            courseArrangeEvaluateRecord.setUserId(userId);
            courseArrangeEvaluateRecord.setUserName(user.getName());
            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            courseArrangeEvaluateRecord.setCreateTime(timestamp);
            courseArrangeEvaluateRecord.setUpdateTime(timestamp);
            courseArrangeEvaluateRecordBiz.save(courseArrangeEvaluateRecord);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        }catch (Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
}
