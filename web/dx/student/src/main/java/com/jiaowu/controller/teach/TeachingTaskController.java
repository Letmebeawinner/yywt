package com.jiaowu.controller.teach;

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
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.teach.TeachingTaskBiz;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.teach.TeachingTask;

/**
 * 教学任务Controller
 * 
 * @author 李帅雷
 *
 */
@Controller
public class TeachingTaskController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(TeachingTaskController.class);

	@Autowired
	private TeachingTaskBiz teachingTaskBiz;
	@Autowired
	private ClassesBiz classesBiz;
	@Autowired
	private ClassTypeBiz classTypeBiz;

	private static final String ADMIN_PREFIX = "/admin/jiaowu/teachingTask";
	private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/teachingTask";

	@InitBinder({ "teachingTask" })
	public void initTeachingTask(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("teachingTask.");
	}

	/**
	 * @Description 跳转到创建教学任务的页面
	 * @author 李帅雷
	 * @param request
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/createTeachingTask")
	public String createTeachingTask(HttpServletRequest request) {
		try {
		} catch (Exception e) {
			logger.info("TeachingTaskController.createTeachingTask", e);
		}
		return "/admin/teachingTask/create_teachingTask";
	}

	/**
	 * @Description 创建教学任务
	 * @param request
	 * @param teachingTask
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/doCreateTeachingTask")
	@ResponseBody
	public Map<String, Object> doCreateTeachingTask(HttpServletRequest request,
			@ModelAttribute("teachingTask") TeachingTask teachingTask) {
		Map<String, Object> json = null;
		try {
			json = validateTeachingTask(teachingTask);
			if (json != null) {
				return json;
			}
			Classes classes = classesBiz.findById(teachingTask.getClassId());
			teachingTask.setClassName(classes.getName());
			teachingTask.setClassTypeId(classes.getClassTypeId());
			ClassType classType = classTypeBiz.findById(classes
					.getClassTypeId());
			teachingTask.setClassTypeName(classType.getName());
			teachingTaskBiz.save(teachingTask);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			logger.info("TeachingTaskController.doCreateTeachingTask", e);
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * 验证教学任务
	 * 
	 * @param teachingTask
	 * @return
	 */
	public Map<String, Object> validateTeachingTask(TeachingTask teachingTask) {
		if (teachingTask.getClassId() == null || teachingTask.getClassId() <= 0) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "班次不能为空",
					null);
		}
		if (StringUtils.isTrimEmpty(teachingTask.getContent())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空",
					null);
		}
		return null;
	}

	/**
	 * @Description 跳转到修改教学任务的页面
	 * @author 李帅雷
	 * @param request
	 * @param id
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/updateTeachingTask")
	public String updateTeachingTask(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			TeachingTask teachingTask = teachingTaskBiz.findById(id);
			request.setAttribute("teachingTask", teachingTask);
		} catch (Exception e) {
			logger.info("TeachingTaskController.updateTeachingTask", e);
		}
		return "/admin/teachingTask/update_teachingTask";
	}

	/**
	 * @Description 修改教学任务
	 * @param request
	 * @param teachingTask
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/doUpdateTeachingTask")
	@ResponseBody
	public Map<String, Object> doUpdateTeachingTask(HttpServletRequest request,
			@ModelAttribute("teachingTask") TeachingTask teachingTask) {
		Map<String, Object> json = null;
		try {
			if (StringUtils.isTrimEmpty(teachingTask.getContent())) {
				return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空",
						null);
			}
			teachingTaskBiz.update(teachingTask);
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
	 * @Description 教学任务列表
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/teachingTaskList")
	public String teachingTaskList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {

			String whereSql = " status=1";
			TeachingTask teachingTask = new TeachingTask();
			String classId = request.getParameter("classId");
			if (!StringUtils.isTrimEmpty(classId)
					&& Long.parseLong(classId) > 0) {
				whereSql += " and classId=" + classId;
				teachingTask.setClassId(Long.parseLong(classId));
				String className=request.getParameter("className");
				request.setAttribute("className", className);
			}
			pagination.setRequest(request);
			List<TeachingTask> teachingTaskList = teachingTaskBiz.find(
					pagination, whereSql);
			request.setAttribute("teachingTaskList", teachingTaskList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("teachingTask", teachingTask);
		} catch (Exception e) {
			e.printStackTrace();
			return this.setErrorPath(request, e);
		}
		return "/admin/teachingTask/teachingTask_list";
	}

	/**
	 * @Description 删除教学任务
	 * @author 李帅雷
	 * @param request
	 * @param id
	 * @return java.util.Map
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/deleteTeachingTask")
	@ResponseBody
	public Map<String, Object> deleteTeachingTask(HttpServletRequest request,
			@RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			TeachingTask teachingTask=new TeachingTask();
			teachingTask.setId(id);
			teachingTask.setStatus(0);
			teachingTaskBiz.update(teachingTask);
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
	 * @Description 查询教学任务详情
	 * @author 李帅雷
	 * @param request
	 * @param id
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/queryTeachingTask")
	public String queryTeachingTask(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			TeachingTask teachingTask = teachingTaskBiz.findById(id);
			request.setAttribute("teachingTask", teachingTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingTask/query_teachingTask";
	}
}
