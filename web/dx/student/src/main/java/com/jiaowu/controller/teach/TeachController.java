package com.jiaowu.controller.teach;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.teachingInfo.TeachingInfoBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classroom.Classroom;
//import com.jiaowu.entity.classes.Classroom;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.teachingInfo.TeachingInfo;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.xinde.XinDe;
import com.a_268.base.util.StringUtils;

/**
 * 教学计划Controller
 * 
 * @author 李帅雷
 *
 */
@Controller
public class TeachController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(TeachController.class);

	@Autowired
	private TeachingProgramCourseBiz teachingProgramCourseBiz;
	@Autowired
	private CourseBiz courseBiz;
	@Autowired
	private ClassesBiz classesBiz;
	@Autowired
	private ClassroomBiz classroomBiz;
	@Autowired
	private CourseArrangeBiz courseArrangeBiz;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private TeachingInfoBiz teachingInfoBiz;

	private static final String ADMIN_PREFIX = "/admin/jiaowu/teach";
	private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/teach";

	@InitBinder({ "teachingProgramCourse" })
	public void initTeachingProgramCourse(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("teachingProgramCourse.");
	}

	@InitBinder({ "courseArrange" })
	public void initCourseArrange(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("courseArrange.");
	}

	/**
	 * @Description 跳转到创建教学计划课程的页面
	 * @author 李帅雷
	 * @param request
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/createTeachingProgramCourse")
	public String createTeachingProgramCourse(HttpServletRequest request) {
		try {
		} catch (Exception e) {
			logger.info("TeachController.createTeachingProgramCourse", e);
		}
		return "/admin/teachingProgramCourse/add_teaching_program_course";
	}

	/**
	 * @Description 创建教学计划课程
	 * @param request
	 * @param teachingProgramCourse
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/doCreateTeachingProgramCourse")
	@ResponseBody
	public Map<String, Object> doCreateTeachingProgramCourse(
			HttpServletRequest request,
			@ModelAttribute("teachingProgramCourse") TeachingProgramCourse teachingProgramCourse) {
		Map<String, Object> json = null;
		try {
			json = validateTeachingProgramCourse(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			json = checkTeachingProgramHasConfirm(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			json = checkTeachingProgramCourseIsDuplicated(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			json = checkClassroomSeatsBiggerThanStudentNum(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			teachingProgramCourseBiz.setMoreInfo(teachingProgramCourse);
			teachingProgramCourseBiz.save(teachingProgramCourse);
			teachingProgramCourseBiz
					.addTeachingInfoOfAddTeachingProgramCourse(teachingProgramCourse);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			logger.info("TeachController.doCreateTeachingProgramCourse", e);
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 教学计划课程列表
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/teachingProgramCourseList")
	public String teachingProgramCourseList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String whereSql = " status=1";
			TeachingProgramCourse teachingProgramCourse = new TeachingProgramCourse();
			teachingProgramCourseBiz.addCondition(request,
					teachingProgramCourse);
			pagination.setRequest(request);
			List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz
					.find(pagination, whereSql);
			teachingProgramCourseBiz
					.setConfirmTeachingProgram(teachingProgramCourseList);
			request.setAttribute("teachingProgramCourseList",
					teachingProgramCourseList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("teachingProgramCourse", teachingProgramCourse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingProgramCourse/teaching_program_course_list";
	}

	/**
	 * @Description 跳转到修改某教学计划课程的页面
	 * @author 李帅雷
	 * @param request
	 * @param id
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/updateTeachingProgramCourse")
	public String updateTeachingProgramCourse(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			TeachingProgramCourse teachingProgramCourse = teachingProgramCourseBiz
					.findById(id);
			request.setAttribute("teachingProgramCourse", teachingProgramCourse);
		} catch (Exception e) {
			logger.info("TeachController.updateTeachingProgramCourse", e);
		}
		return "/admin/teachingProgramCourse/update_teaching_program_course";
	}

	/**
	 * @Description 修改某教学计划课程
	 * @author 李帅雷
	 * @param request
	 * @param teachingProgramCourse
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/doUpdateTeachingProgramCourse")
	@ResponseBody
	public Map<String, Object> doUpdateTeachingProgramCourse(
			HttpServletRequest request,
			@ModelAttribute("teachingProgramCourse") TeachingProgramCourse teachingProgramCourse) {
		Map<String, Object> json = null;
		try {
			json = validateTeachingProgramCourse(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			json = checkTeachingProgramHasConfirm(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			json = checkTeachingProgramCourseIsDuplicated(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			json = checkClassroomSeatsBiggerThanStudentNum(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			teachingProgramCourseBiz.setMoreInfo(teachingProgramCourse);
			teachingProgramCourseBiz.update(teachingProgramCourse);
			teachingProgramCourseBiz
					.addTeachingInfoOfUpdateTeachingProgramCourse(teachingProgramCourse);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			logger.info("TeachController.doUpdateTeachingProgramCourse", e);
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 删除某教学计划课程
	 * @param request
	 * @param id
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/deleteTeachingProgramCourse")
	@ResponseBody
	public Map<String, Object> deleteTeachingProgramCourse(
			HttpServletRequest request, @RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			TeachingProgramCourse teachingProgramCourse = teachingProgramCourseBiz
					.findById(id);
			json = checkTeachingProgramHasConfirm(teachingProgramCourse);
			if (json != null) {
				return json;
			}
			teachingProgramCourse.setStatus(0);
			teachingProgramCourseBiz.update(teachingProgramCourse);
			teachingProgramCourseBiz
					.addTeachingInfoOfDeleteTeachingProgramCourse(teachingProgramCourse);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			logger.info("TeachController.deleteTeachingProgramCourse", e);
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 确认班次教学计划
	 * @param request
	 * @param pagination
	 * @return String
	 */
	@RequestMapping(ADMIN_PREFIX + "/confirmTeachingProgramList")
	public String confirmTeachingProgramList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String whereSql = " status=1";
			pagination.setRequest(request);
			Classes classes = new Classes();
			String classNumber = request.getParameter("classNumber");
			if (!StringUtils.isTrimEmpty(classNumber)) {
				whereSql += " and classNumber like '%" + classNumber + "%'";
				classes.setClassNumber(classNumber);
			}
			List<Classes> classList = classesBiz.find(pagination, whereSql);
			request.setAttribute("classList", classList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("classes", classes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingProgramCourse/confirm_teaching_program";
	}

	/**
	 * @Description 确认/取消确认教学计划
	 * @param request
	 * @param id
	 * @param confirmTeachingProgram
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX + "/confirmTeachingProgram")
	@ResponseBody
	public Map<String, Object> confirmTeachingProgram(
			HttpServletRequest request,
			@RequestParam("id") Long id,
			@RequestParam("confirmTeachingProgram") Integer confirmTeachingProgram) {
		Map<String, Object> json = null;
		try {
			Classes classes = classesBiz.findById(id);
			classes.setConfirmTeachingProgram(confirmTeachingProgram);
			classesBiz.update(classes);
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
	 * @Description 一个班次教学计划详情
	 * @param request
	 * @param pagination
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX + "/detailTeachingProgramOfOneClass")
	public String detailTeachingProgramOfOneClass(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String classId = request.getParameter("classId");
			String whereSql = " classId=" + classId;
			pagination.setRequest(request);
			List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz
					.find(pagination, whereSql);
			request.setAttribute("teachingProgramCourseList",
					teachingProgramCourseList);
			request.setAttribute("pagination", pagination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingProgramCourse/detail_teaching_program_of_one_class";
	}

	/**
	 * @Description 一个班次教学计划详情
	 * @param request
	 * @param pagination
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX + "/detailTeachingProgramOfOneStudent")
	public String detailTeachingProgramOfOneStudent(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String userId = request.getParameter("userId");
			User user = userBiz.findById(userId);
			String whereSql = " classId=" + user.getClassId();
			pagination.setRequest(request);
			List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz
					.find(pagination, whereSql);
			request.setAttribute("teachingProgramCourseList",
					teachingProgramCourseList);
			request.setAttribute("pagination", pagination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingProgramCourse/detail_teaching_program_of_one_student";
	}

	/**
	 * @Description 跳转到更改排课的教室页面,改排课表
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX + "/toUpdateClassroom")
	public String toUpdateClassroom(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			CourseArrange courseArrange = courseArrangeBiz.findById(id);
			request.setAttribute("courseArrange", courseArrange);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingProgramCourse/update_classroom";
	}

	/**
	 * @Description 排课页面中更改课的教室,改排课表
	 * @param request
	 * @param teachingProgramCourse
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX + "/updateClassroom")
	@ResponseBody
	public Map<String, Object> updateClassroom(HttpServletRequest request,
			@ModelAttribute("courseArrange") CourseArrange courseArrange) {
		Map<String, Object> json = null;
		try {
			courseArrangeBiz.update(courseArrange);
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
	 * @Description 导出教学计划
	 * @author 李帅雷
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/exportExcel")
	public void userListExport(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("")Long classId) {
		try {
			String dir = request.getSession().getServletContext()
					.getRealPath("/excelfile/xinde");
			Classes classes = classesBiz.findById(classId);
			String expName = classes.getName() + "教学计划";
			String[] headName = { "ID", "课程ID", "课程名称", "班次ID", "班次名称", "教师ID",
					"教师名称", "备注", "班级ID", "班级名称", "创建时间", "更新时间" };
			Pagination pagination = new Pagination();
			pagination.setPageSize(10000);
			String whereSql = " classId=" + classId;
			pagination.setRequest(request);
			teachingProgramCourseBiz.find(pagination, whereSql);
			int num = pagination.getTotalPages();// 总页数
			List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
			for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
				pagination.setCurrentPage(i);
				List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz
						.find(pagination, whereSql);
				List<List<String>> list = convert(teachingProgramCourseList);
				File file = FileExportImportUtil.createExcel(headName, list,
						expName + "_" + i, dir);
				srcfile.add(file);
			}
			FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将教学计划课程的集合转变为List<List<String>>
	 * @param teachingProgramCourseList
	 * @return
	 */
	public List<List<String>> convert(
			List<TeachingProgramCourse> teachingProgramCourseList) {
		List<List<String>> list = new ArrayList<List<String>>();
		if (teachingProgramCourseList != null
				&& teachingProgramCourseList.size() > 0) {
			for (TeachingProgramCourse teachingProgramCourse : teachingProgramCourseList) {
				List<String> smallList = new ArrayList<String>();
				smallList.add(teachingProgramCourse.getId() + "");
				smallList.add(teachingProgramCourse.getCourseId() + "");
				smallList.add(teachingProgramCourse.getCourseName() + "");
				smallList.add(teachingProgramCourse.getClassId() + "");
				smallList.add(teachingProgramCourse.getClassName() + "");
				smallList.add(teachingProgramCourse.getTeacherId() + "");
				smallList.add(teachingProgramCourse.getTeacherName() + "");
				smallList.add(teachingProgramCourse.getNote() + "");
				smallList.add(teachingProgramCourse.getClassroomId() + "");
				smallList.add(teachingProgramCourse.getClassroomName() + "");
				smallList.add(teachingProgramCourse.getCreateTime() + "");
				smallList.add(teachingProgramCourse.getUpdateTime() + "");
				list.add(smallList);
			}
		}
		return list;
	}

	public String dateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 验证教学计划课程
	 * 
	 * @param teachingProgramCourse
	 * @return
	 */
	public Map<String, Object> validateTeachingProgramCourse(
			TeachingProgramCourse teachingProgramCourse) {
		if (teachingProgramCourse.getCourseId() == null
				|| teachingProgramCourse.getCourseId() <= 0) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "课程不能为空",
					null);
		}
		if (teachingProgramCourse.getClassId() == null
				|| teachingProgramCourse.getClassId() <= 0) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "班次不能为空",
					null);
		}
		if (teachingProgramCourse.getClassroomId() == null
				|| teachingProgramCourse.getClassroomId() <= 0) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "教室不能为空",
					null);
		}
		return null;
	}
	/**
	 * 查询某班次的教学计划是否已确定
	 * @param teachingProgramCourse
	 * @return
	 */
	public Map<String, Object> checkTeachingProgramHasConfirm(
			TeachingProgramCourse teachingProgramCourse) {
		Classes classes = classesBiz.findById(teachingProgramCourse
				.getClassId());
		if (classes.getConfirmTeachingProgram() == 1) {
			return this.resultJson(ErrorCode.ERROR_DATA, "该班次教学计划已确定,无法新增!",
					null);
		}
		return null;
	}
	/**
	 * 查询某班次是否已存在该教学计划课程
	 * @param teachingProgramCourse
	 * @return
	 */
	public Map<String, Object> checkTeachingProgramCourseIsDuplicated(
			TeachingProgramCourse teachingProgramCourse) {
		List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz
				.find(null,
						" status=1 and classId="
								+ teachingProgramCourse.getClassId()
								+ " and courseId="
								+ teachingProgramCourse.getCourseId()+" and id!="+teachingProgramCourse.getId());
		if (teachingProgramCourseList != null
				&& teachingProgramCourseList.size() > 0) {
			return this.resultJson(ErrorCode.ERROR_DATA,
					"该班次已存在该课程,请重新选择班次或课程!", null);
		}
		return null;
	}
	/**
	 * 查询某教室的座位数是否大于班次的人数
	 * @param teachingProgramCourse
	 * @return
	 */
	public Map<String, Object> checkClassroomSeatsBiggerThanStudentNum(
			TeachingProgramCourse teachingProgramCourse) {
		Classes classes = classesBiz.findById(teachingProgramCourse
				.getClassId());
		// 判断教室座位数是否大于班次人数
		Classroom classroom = classroomBiz.findById(teachingProgramCourse
				.getClassroomId());
		if (classroom.getNum() < classes.getStudentTotalNum()) {
			return this.resultJson(ErrorCode.ERROR_DATA,
					"该教室座位数小于该班次人数,请重新选择!", null);
		}
		return null;
	}
}
