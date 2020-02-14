package com.jiaowu.controller.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.courseType.CourseTypeBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.courseType.CourseType;

/**
 * 课程Controller
 * @author 李帅雷
 *
 */
@Controller
public class CourseController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@Autowired
	private CourseBiz courseBiz;
	@Autowired
	private CourseTypeBiz courseTypeBiz;
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/course";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/course";
	 
	@InitBinder({"course"})
	public void initCourse(WebDataBinder binder){
		binder.setFieldDefaultPrefix("course.");
	}
	
	/**
     * @Description 跳转到创建课程的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateCourse")
	public String toCreateCourse(HttpServletRequest request){
		try{			
			courseTypeBiz.setAttributeCourseTypeList(request);
		}catch(Exception e){
			logger.info("CourseController.toCreateCourse",e);
		}
		return "/admin/course/create_course";
	}
	
	/**
     * @Description 创建课程
     * @param request
     * @param course
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createCourse")
	@ResponseBody
	public Map<String,Object> createCourse(HttpServletRequest request,@ModelAttribute("course")Course course){
		Map<String,Object> json=null;
		try{
			json=validateCourse(course);
			if(json!=null){
				return json;
			}
			CourseType courseType=courseTypeBiz.findById(course.getCourseTypeId());
			course.setCourseTypeName(courseType.getName());
			courseBiz.save(course);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			logger.info("CourseController.createCourse",e);
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description 跳转到修改课程的页面
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     */
	@RequestMapping(ADMIN_PREFIX+"/toUpdateCourse")
	public String toUpdateCourse(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			courseTypeBiz.setAttributeCourseTypeList(request);
			Course course=courseBiz.findById(id);
			course.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(course.getTeacherId()));
			request.setAttribute("course", course);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/course/update_course";
	}
	
	/**
     * @Description 修改课程
     * @param request
     * @param course
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/updateCourse")
	@ResponseBody
	public Map<String,Object> updateCourse(HttpServletRequest request,@ModelAttribute("course")Course course){
		Map<String,Object> json=null;
		try{
			validateCourse(course);
			CourseType courseType=courseTypeBiz.findById(course.getCourseTypeId());
			course.setCourseTypeName(courseType.getName());
			courseBiz.update(course);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
	/**
     * @Description 课程列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/courseList")
	public String courseList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{			
			courseTypeBiz.setAttributeCourseTypeList(request);
			 String whereSql = " status=1";
			 Course course=new Course();
			 whereSql+=courseBiz.addCondition(request,course);
			 pagination.setRequest(request);
	         List<Course> courseList = courseBiz.find(pagination,whereSql);
			if(courseList!=null&&courseList.size()>0){
				for(Course tempCourse:courseList){
					tempCourse.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(tempCourse.getTeacherId()));
				}
			}
	         request.setAttribute("courseList",courseList);
	         request.setAttribute("pagination",pagination);
	         request.setAttribute("course", course);
		}catch(Exception e){
			logger.info("CourseController.courseList",e);
		}
		return "/admin/course/course_list";
	}
	
	/**
     * @Description 弹出框形式的课程列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(WITHOUT_ADMIN_PREFIX+"/courseListForSelect")
	public String courseListForSelect(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{			
			courseTypeBiz.setAttributeCourseTypeList(request);
			 String whereSql = " status=1";
			 Course course=new Course();
			 whereSql+=courseBiz.addCondition(request, course);
			 pagination.setCurrentUrl(courseBiz.getCurrentUrl(request, pagination, course));
	         List<Course> courseList = courseBiz.find(pagination,whereSql);
			if(courseList!=null&&courseList.size()>0){
				for(Course tempCourse:courseList){
					tempCourse.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(tempCourse.getTeacherId()));
				}
			}
	         request.setAttribute("courseList",courseList);
	         request.setAttribute("pagination",pagination);
	         request.setAttribute("course", course);
		}catch(Exception e){
			logger.info("CourseController.courseList",e);
		}
		return "/admin/course/course_list_for_select";
	}
	
	/**
     * @Description 删除课程
     * @param request
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/delCourse")
	@ResponseBody
	public Map<String,Object> delCourse(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			Course course=courseBiz.findById(id);
			course.setStatus(0);
			courseBiz.update(course);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			logger.info("CourseController.courseList",e);
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
	 * 验证课程
	 * @param course
	 * @return
	 */
	public Map<String,Object> validateCourse(Course course){
		Map<String,Object> error=null;
		if(StringUtils.isTrimEmpty(course.getName())){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空", null);
			return error;
		}
		/*if(course.getTeacherId()==null||course.getTeacherId()<=0){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "教师不能为空", null);
            return error;
        }*/
		if(StringUtils.isTrimEmpty(course.getTeacherId())){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "教师不能为空", null);
			return error;
		}
		if(course.getCourseTypeId()==null||course.getCourseTypeId()<=0){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "课程类别不能为空", null);
            return error;
        }
		if(course.getHour()==null){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "学时不能为空", null);
            return error;
		}
		if(course.getHour().equals(0f)){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "学时不能为0", null);
            return error;
		}
		return error;
	}
}
