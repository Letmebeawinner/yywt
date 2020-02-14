package com.jiaowu.controller.classroom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.course.CourseArrange;

/**
 * 教室Controller
 * 
 * @author 李帅雷
 *
 */
@Controller
public class ClassroomController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ClassroomController.class);

	@Autowired
	private ClassroomBiz classroomBiz;
	@Autowired
	private CourseArrangeBiz courseArrangeBiz;

	@InitBinder({ "classroom" })
	public void initClassroom(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("classroom.");
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	private static final String ADMIN_PREFIX = "/admin/jiaowu/classroom";
	private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/classroom";

	/**
	 * @Description 跳转到创建教室的页面
	 * @author 李帅雷
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/toCreateClassroom")
	public String toCreateClassroom() {
		return "/admin/classroom/create_classroom";
	}

	/**
	 * @Description 创建教室
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/createClassroom")
	@ResponseBody
	public Map<String, Object> createClassroom(HttpServletRequest request,
			@ModelAttribute("classroom") Classroom classroom) {
		Map<String, Object> json = null;
		try {
			json = validateClassroom(request, classroom);
			if (json != null) {
				return json;
			}
			classroom.setStatus(1);
			classroomBiz.save(classroom);
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
	 * @Description 教室列表
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/classroomList")
	public String classroomList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String whereSql = " status=1";
			Classroom classroom = new Classroom();
			whereSql += classroomBiz.addCondition(request, classroom);
			pagination.setRequest(request);
			List<Classroom> classroomList = classroomBiz.find(pagination,
					whereSql);
			request.setAttribute("classroomList", classroomList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("classroom", classroom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/classroom/classroom_list";
	}

	/**
	 * @Description 弹出框形式的教室列表页面
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(WITHOUT_ADMIN_PREFIX + "/classroomListForSelect")
	public String classroomListForSelect(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String whereSql = " status=1";
			Classroom classroom = new Classroom();
			whereSql += classroomBiz.addCondition(request, classroom);
			pagination.setCurrentUrl(classroomBiz.getCurrentUrl(request,
					pagination, classroom));
			List<Classroom> classroomList = classroomBiz.find(pagination,
					whereSql);
			request.setAttribute("classroomList", classroomList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("classroom", classroom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/classroom/classroom_list_forSelect";
	}

	/**
	 * @Description 教室使用情况
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/usePosition")
	public String usePosition(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination,@RequestParam("classroomId")Long classroomId) {
		try {
			String whereSql = " classroomId=" + classroomId;
			CourseArrange courseArrange = new CourseArrange();
			courseArrange.setClassroomId(classroomId);
			whereSql+=classroomBiz.addCondition(request, courseArrange);
			pagination.setRequest(request);
			List<CourseArrange> courseArrangeList = courseArrangeBiz.find(
					pagination, whereSql);
			request.setAttribute("courseArrangeList", courseArrangeList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("courseArrange", courseArrange);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/classroom/use_position";
	}

	/**
	 * @Description 删除教室
	 * @param request
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/deleteClassroom")
	@ResponseBody
	public Map<String, Object> deleteClassroom(HttpServletRequest request,
			@RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			Classroom classroom = new Classroom();
			classroom.setId(id);
			classroom.setStatus(0);
			classroomBiz.update(classroom);
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
	 * @Description 跳转到修改教室的页面
	 * @param request
	 * @param id
	 * @return String
	 */
	@RequestMapping(ADMIN_PREFIX + "/toUpdateClassroom")
	public String toUpdateClassroom(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			Classroom classroom = classroomBiz.findById(id);
			request.setAttribute("classroom", classroom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/classroom/update_classroom";
	}

	/**
	 * @Description 修改教室
	 * @param request
	 * @param classroom
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/updateClassroom")
	@ResponseBody
	public Map<String, Object> updateClassroom(HttpServletRequest request,
			@ModelAttribute("classroom") Classroom classroom) {
		Map<String, Object> json = null;
		try {
			json=validateClassroom(request, classroom);
			if(json!=null){
				return json;
			}
			classroomBiz.update(classroom);
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
	 * 验证教室
	 * 
	 * @param request
	 * @param classroom
	 * @return
	 */
	public Map<String, Object> validateClassroom(HttpServletRequest request,
			Classroom classroom) {
		Map<String, Object> error = null;
		if (StringUtils.isTrimEmpty(classroom.getPosition())) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "位置不能为空",
					null);
			return error;
		}
		return null;
	}
}
