/**
 * 
 */
package com.jiaowu.controller.courseType;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.jiaowu.biz.courseType.CourseTypeBiz;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.courseType.CourseType;

/**
 * 课程类别Controller
 * 
 * @author 李帅雷
 *
 */
@Controller
public class CourseTypeController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(CourseTypeController.class);
	@Autowired
	private CourseTypeBiz courseTypeBiz;

	private static final String ADMIN_PREFIX = "/admin/jiaowu/courseType";
	private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/courseType";

	@InitBinder({ "courseType" })
	public void initCourseType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("courseType.");
	}

	/**
	 * @Description 跳转到创建课程类别的页面
	 * @author 李帅雷
	 * @param request
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/toCreateCourseType")
	public String toCreateCourseType(HttpServletRequest request) {
		try {

		} catch (Exception e) {
			logger.info("CourseTypeController.toCreateCourseType", e);
		}
		return "/admin/courseType/create_course_type";
	}

	/**
	 * @Description 创建课程类别
	 * @param request
	 * @param courseType
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/createCourseType")
	@ResponseBody
	public Map<String, Object> createCourseType(HttpServletRequest request,
			@ModelAttribute("courseType") CourseType courseType) {
		Map<String, Object> json = null;
		try {
			json = validateCourseType(courseType);
			if (json != null) {
				return json;
			}
			courseTypeBiz.save(courseType);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			logger.info("CourseTypeController.createCourseType", e);
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 跳转到修改课程类别的页面上
	 * @author 李帅雷
	 * @param request
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/toUpdateCourseType")
	public String toUpdateCourseType(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			CourseType courseType = courseTypeBiz.findById(id);
			request.setAttribute("courseType", courseType);
		} catch (Exception e) {
			logger.info("CourseTypeController.toUpdateCourseType", e);
		}
		return "/admin/courseType/update_course_type";
	}

	/**
	 * @Description 修改课程类别
	 * @param request
	 * @param courseType
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/updateCourseType")
	@ResponseBody
	public Map<String, Object> updateCourseType(HttpServletRequest request,
			@ModelAttribute("courseType") CourseType courseType) {
		Map<String, Object> json = null;
		try {
			json = validateCourseType(courseType);
			if (json != null) {
				return json;
			}
			courseTypeBiz.update(courseType);
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
	 * @Description 课程类别列表
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/courseTypeList")
	public String courseTypeList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination,
			@ModelAttribute("courseType") CourseType courseType) {
		try {
			String whereSql = " status=1";
			if(!StringUtils.isTrimEmpty(courseType.getName())){
				whereSql += " and name like '%"+courseType.getName()+"%'";
			}
			pagination.setRequest(request);
			List<CourseType> courseTypeList = courseTypeBiz.find(pagination,
					whereSql);
			request.setAttribute("courseTypeList", courseTypeList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("courseType", courseType);
		} catch (Exception e) {
			e.printStackTrace();
			return this.setErrorPath(request, e);
		}
		return "/admin/courseType/course_type_list";
	}

	/**
	 * @Description 删除课程类别
	 * @param request
	 * @param id
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/delCourseType")
	@ResponseBody
	public Map<String, Object> delCourseType(HttpServletRequest request,
			@RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			CourseType courseType = new CourseType();
			courseType.setId(id);
			courseType.setStatus(0);
			courseTypeBiz.update(courseType);
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
	 * 验证课程类别
	 * 
	 * @param courseType
	 * @return
	 */
	public Map<String, Object> validateCourseType(CourseType courseType) {
		if (StringUtils.isTrimEmpty(courseType.getName())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空",
					null);
		}
		return null;
	}
}
