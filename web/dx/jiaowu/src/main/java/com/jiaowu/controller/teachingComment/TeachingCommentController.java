package com.jiaowu.controller.teachingComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.course.CourseArrange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.teachingComment.TeachingCommentBiz;
import com.jiaowu.biz.teachingComment.TeachingCommentManagementBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.enums.UserType;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.teachingComment.TeachingComment;
import com.jiaowu.entity.teachingComment.TeachingCommentManagement;

/**
 * 教学质量监控Controller
 *
 * @author 李帅雷
 */
@Controller
public class TeachingCommentController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TeachingCommentController.class);

    @Autowired
    private TeachingCommentBiz teachingCommentBiz;
    @Autowired
    private TeachingCommentManagementBiz teachingCommentManagementBiz;
    @Autowired
    private CourseBiz courseBiz;
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private CommonBiz commonBiz;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    private ClassesBiz classesBiz;

    @InitBinder({"teachingComment"})
    public void initTeachingComment(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("teachingComment.");
    }

    @InitBinder({"teachingCommentManagement"})
    public void initTeachingCommentManagement(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("teachingCommentManagement.");
    }


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private static final String ADMIN_PREFIX = "/admin/jiaowu/teachingComment";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/teachingComment";
    /**
     * 获取课程列表
     * @param pagination
     * @param whereSQL
     * @return
     */
   /* public List<Course> getCourseList(Pagination pagination,String whereSQL){
        return courseBiz.find(pagination,whereSQL);
    }*/

    /**
     * @Description 跳转到创建教学评价类别的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     *
     */
    /*@RequestMapping(ADMIN_PREFIX+"/toCreateTeachingComment")
    public String toCreateTeachingComment(HttpServletRequest request){
		try{
			List<Course> courseList=getCourseList(null," 1=1");
            request.setAttribute("courseList",courseList);
		}catch(Exception e){
			logger.info("TeachingCommentController.toCreateTeachingComment",e);
		}
		return createTeachingComment;
	}*/

    /**
     * @Description 创建教学工作评价类别
     * @param request
     * @param teachingComment
     * @return java.util.Map
     */
    /*@RequestMapping(ADMIN_PREFIX+"/createTeachingComment")
    @ResponseBody
	public Map<String,Object> createTeachingComment(HttpServletRequest request,@ModelAttribute("teachingComment")TeachingComment teachingComment){
		Map<String,Object> json=null;
		try{
			if(teachingComment.getCourseId()==null||teachingComment.getCourseId()<=0){
                json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "课程不能为空", null);
                return json;
            }
			if(teachingComment.getStartTime()==null){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "开始时间不能为空", null);
				return json;
			}
			if(teachingComment.getEndTime()==null){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "结束时间不能为空", null);
				return json;
			}
			if(teachingComment.getStartTime().getTime()>=teachingComment.getEndTime().getTime()){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "开始时间不能大于结束时间", null);
				return json;
			}
			teachingComment.setStatus(1);
			teachingCommentBiz.save(teachingComment);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			logger.info("TeachingCommentController.createTeachingComment",e);
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}*/


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 教学工作评价类别列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/teachingCommentList")
    public String teachingCommentList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            /*List<Course> courseList=getCourseList(null," 1=1");
            request.setAttribute("courseList",courseList);*/

            String whereSql = " status=1";
            TeachingComment teachingComment = new TeachingComment();
            String courseId = request.getParameter("courseId");
            String courseName = request.getParameter("courseName");
            if (!StringUtils.isTrimEmpty(courseId) && Long.parseLong(courseId) > 0) {
                whereSql += " and courseId=" + courseId;
                teachingComment.setCourseId(Long.parseLong(courseId));
                teachingComment.setCourseName(courseName);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = request.getParameter("startTime");
            if (!StringUtils.isTrimEmpty(startTime)) {
                whereSql += " and startTime > '" + startTime + "'";
                teachingComment.setStartTime(sdf.parse(startTime));
            }
            String endTime = request.getParameter("endTime");
            if (!StringUtils.isTrimEmpty(endTime)) {
                whereSql += " and endTime < '" + endTime + "'";
                teachingComment.setEndTime(sdf.parse(endTime));
            }
            pagination.setRequest(request);
            List<TeachingComment> teachingCommentList = teachingCommentBiz.find(pagination, whereSql);
            request.setAttribute("teachingCommentList", teachingCommentList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teachingComment", teachingComment);
        } catch (Exception e) {
            logger.info("TeachingCommentController.teachingCommentList", e);
            e.printStackTrace();
        }
        return "/admin/teachingComment/teachingComment_list";
    }

    /**
     * @param request
     * @param id
     * @return java.lang.String
     * @Description 跳转到修改教学评价类别的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateTeachingComment")
    public String toUpdateTeachingComment(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            TeachingComment teachingComment = teachingCommentBiz.findById(id);
            request.setAttribute("teachingComment", teachingComment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/update_teachingComment";
    }

    /**
     * @param request
     * @param teachingComment
     * @return java.util.Map
     * @Description 修改教学工作评价类别
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/updateTeachingComment")
    @ResponseBody
    public Map<String, Object> updateTeachingComment(HttpServletRequest request, @ModelAttribute("teachingComment") TeachingComment teachingComment) {
        Map<String, Object> json = null;
        try {
            teachingCommentBiz.update(teachingComment);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的教学工作评价类别列表
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/teachingCommentListForSelect")
    public String teachingCommentListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " status=1";
            Map<String, Object> map = teachingCommentBiz.addCondition(request);
            whereSql += map.get("condition");
            TeachingComment teachingComment = (TeachingComment) map.get("teachingComment");
            pagination.setCurrentUrl(teachingCommentBiz.getCurrentUrl(request, pagination, teachingComment));
            List<TeachingComment> teachingCommentList = teachingCommentBiz.find(pagination, whereSql);
            request.setAttribute("teachingCommentList", teachingCommentList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teachingComment", teachingComment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/teachingComment_list_forSelect";
    }

    /**
     * @param request
     * @param id
     * @return java.util.Map
     * @Description 删除某教学工作评价类别
     */
    @RequestMapping(ADMIN_PREFIX + "/delTeachingComment")
    @ResponseBody
    public Map<String, Object> delTeachingComment(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            TeachingComment teachingComment = new TeachingComment();
            teachingComment.setId(id);
            teachingComment.setStatus(0);
            teachingCommentBiz.update(teachingComment);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.info("TeachingCommentController.delTeachingComment", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到创建教学工作评价的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateTeachingCommentManagement")
    public String toCreateTeachingCommentManagement(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/create_teachingCommentManagement";
    }


    /**
     * @param request
     * @param teachingCommentManagement
     * @return java.util.Map
     * @Description 创建教学工作评价
     */
    @RequestMapping(ADMIN_PREFIX + "/createTeachingCommentManagement")
    @ResponseBody
    public Map<String, Object> createTeachingCommentManagement(HttpServletRequest request, @ModelAttribute("teachingCommentManagement") TeachingCommentManagement teachingCommentManagement) {
        Map<String, Object> json = null;
        try {
            json = validateTeachingCommentManagement(teachingCommentManagement);
            if (json != null) {
                return json;
            }
            TeachingComment teachingComment = teachingCommentBiz.findById(teachingCommentManagement.getTeachingCommentId());
            teachingCommentManagement.setCourseId(teachingComment.getCourseId());
            teachingCommentManagement.setCourseName(teachingComment.getCourseName());
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap != null) {
                teachingCommentManagement.setFromPeopleId(Long.parseLong(userMap.get("linkId")));
                teachingCommentManagement.setFromPeopleName(userMap.get("userName"));
                teachingCommentManagement.setType(teachingCommentManagementBiz.getType(request));
            }
            teachingCommentManagementBiz.save(teachingCommentManagement);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 教学工作评价列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/teachingCommentManagementList")
    public String teachingCommentManagementList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " status in (1,2)";
            TeachingCommentManagement teachingCommentManagement = new TeachingCommentManagement();
            String teachingCommentId = request.getParameter("teachingCommentId");
            if (!StringUtils.isTrimEmpty(teachingCommentId) && Long.parseLong(teachingCommentId) > 0) {
                whereSql += " and teachingCommentId=" + teachingCommentId;
                teachingCommentManagement.setTeachingCommentId((Long.parseLong(teachingCommentId)));
                TeachingComment teachingComment = teachingCommentBiz.findById(Long.parseLong(teachingCommentId));
                teachingCommentManagement.setCourseName(teachingComment.getCourseName());
            }
            pagination.setRequest(request);
            List<TeachingCommentManagement> teachingCommentManagementList = teachingCommentManagementBiz.find(pagination, whereSql);
            request.setAttribute("teachingCommentManagementList", teachingCommentManagementList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teachingCommentManagement", teachingCommentManagement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/teachingCommentManagement_list";
    }

    /**
     * @param request
     * @param id
     * @return java.lang.String
     * @Description 某教学工作评价详情
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/queryTeachingCommentManagement")
    public String queryTeachingCommentManagement(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            TeachingCommentManagement teachingCommentManagement = teachingCommentManagementBiz.findById(id);
            request.setAttribute("teachingCommentManagement", teachingCommentManagement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/query_teachingCommentManagement";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 查询当前登录学员未完成的教学评价, 只有学员有此权限.
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/queryStudentNotFinishedTeachingComment")
    public String queryStudentNotFinishedTeachingComment(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        if (commonBiz.getCurrentUserType(request).equals(UserType.student)) {

            pagination.setRequest(request);
            List<TeachingCommentManagement> teachingCommentManagementList = teachingCommentManagementBiz.getTeachingCommentManagementList(request, pagination);
            request.setAttribute("teachingCommentManagementList", teachingCommentManagementList);
            request.setAttribute("pagination", pagination);
        }
        return "/admin/teachingComment/query_student_not_finished_teaching_comment";
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 根据当前登录的角色跳转到不同的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toTeachingCommentListByRole")
    public String toTeachingCommentListByRole(HttpServletRequest request) {
        try {
            if (commonBiz.getCurrentUserType(request).equals(UserType.student)) {
                return "redirect:" + ADMIN_PREFIX + "/queryStudentNotFinishedTeachingComment";
            } else {
                return "redirect:" + ADMIN_PREFIX + "/teachingCommentList";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param request
     * @param id
     * @return java.lang.String
     * @Description 跳转到修改教学评价的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateTeachingCommentManagement")
    public String toUpdateTeachingCommentManagement(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            TeachingCommentManagement teachingCommentManagement = teachingCommentManagementBiz.findById(id);
            request.setAttribute("teachingCommentManagement", teachingCommentManagement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/update_teachingCommentManagement";
    }

    /**
     * @param request
     * @param teachingCommentManagement
     * @return java.util.Map
     * @Description 修改教学评价
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/updateTeachingCommentManagement")
    @ResponseBody
    public Map<String, Object> updateTeachingCommentManagement(HttpServletRequest request, @ModelAttribute("teachingCommentManagement") TeachingCommentManagement teachingCommentManagement) {
        Map<String, Object> json = null;
        try {
            teachingCommentManagement.setStatus(1);
            teachingCommentManagementBiz.update(teachingCommentManagement);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 查询教师自己的教学工作评价类别列表, 此权限只有教师拥有.
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/queryTeacherTeachingCommentList")
    public String queryTeacherTeachingCommentList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            if (commonBiz.getCurrentUserType(request).equals(UserType.teacher)) {
                Long userId = commonBiz.getCurrentUserId(request);
                List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz.find(null, " status=1 and teacherId=" + userId);
                if (teachingProgramCourseList != null && teachingProgramCourseList.size() > 0) {
                    List<TeachingComment> teachingCommentList = teachingCommentBiz.find(pagination, " status=1 and teachingProgramCourseId in " + teachingCommentBiz.getTeachingProgramCourseIds(teachingProgramCourseList));
                    request.setAttribute("teachingCommentList", teachingCommentList);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/query_teacher_teachingComment_list";
    }

    /**
     * @param request
     * @param pagination
     * @param teachingCommentId
     * @return java.lang.String
     * @Description 一个教学评价类别下的所有教学评价
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/teachingCommentManagementListOfOneTeachingComment")
    public String teachingCommentManagementListOfOneTeachingComment(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @RequestParam("teachingCommentId") Long teachingCommentId) {
        try {
            String whereSql = " status=1 and teachingCommentId=" + teachingCommentId;
            pagination.setRequest(request);
            List<TeachingCommentManagement> teachingCommentManagementList = teachingCommentManagementBiz.find(pagination, whereSql);
            request.setAttribute("teachingCommentManagementList", teachingCommentManagementList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teachingCommentId", teachingCommentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/teachingCommentManagement_list_of_one_teachingComment";
    }

    /**
     * @param request
     * @param id
     * @return java.util.Map
     * @Description 删除某教学工作评价
     */
    @RequestMapping(ADMIN_PREFIX + "/delTeachingCommentManagement")
    @ResponseBody
    public Map<String, Object> delTeachingCommentManagement(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            TeachingCommentManagement teachingCommentManagement = new TeachingCommentManagement();
            teachingCommentManagement.setId(id);
            teachingCommentManagement.setStatus(0);
            teachingCommentManagementBiz.update(teachingCommentManagement);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.info("TeachingCommentController.delTeachingCommentManagement", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证教学工作评价
     *
     * @param teachingCommentManagement
     * @return
     */
    public Map<String, Object> validateTeachingCommentManagement(TeachingCommentManagement teachingCommentManagement) {
        Map<String, Object> error = null;
        if (teachingCommentManagement.getTeachingCommentId() == null || teachingCommentManagement.getTeachingCommentId() <= 0) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "教学工作评价类别不能为空", null);
            return error;
        }
        if (teachingCommentManagement.getToPeopleId() == null || teachingCommentManagement.getToPeopleId() <= 0) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "被评价人不能为空", null);
            return error;
        }
        if (StringUtils.isTrimEmpty(teachingCommentManagement.getContent())) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空", null);
            return error;
        }
        return null;
    }


    /**
     * @param request
     * @return java.util.Map
     * @Description 获取某班次课表
     */
    @RequestMapping("/admin/jiaowu/teachingComment/courseArrangeList")
    public String courseArrangeList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {

            //查询班次列表
            List<Classes> classList = classesBiz.find(null, "1=1");
            request.setAttribute("classList", classList);

            String classesId = request.getParameter("classTypeId");
            String whereSql = " 1=1";
            if (classesId != null && classesId != ""&&Integer.parseInt(classesId)!=0) {
                whereSql += " and classId="+classesId;
            }
            pagination.setPageSize(15);
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(pagination, whereSql);
            if (courseArrangeList != null && courseArrangeList.size() > 0) {
                for (CourseArrange courseArrange : courseArrangeList) {
                    TeachingProgramCourse teachingProgramCourse = teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse.getCourseName());

                }
            }
            request.setAttribute("courseArrangeList", courseArrangeList);
            request.setAttribute("classesId",classesId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachingComment/courseArrangeList";
    }


}
