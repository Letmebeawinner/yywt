package com.jiaowu.controller.classroom;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.biz.common.HqHessionService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.classroom.ClassroomDTO;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.meeting.Meeting;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 教室Controller
 * 
 * @author 李帅雷
 *
 */
@Controller
public class ClassroomController extends BaseController {

	private static final String ADMIN_PREFIX = "/admin/jiaowu/classroom";
	private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/classroom";
	private static Logger logger = LoggerFactory
			.getLogger(ClassroomController.class);
	@Autowired
	private ClassroomBiz classroomBiz;
	@Autowired
	private CourseArrangeBiz courseArrangeBiz;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private HqHessionService hqHessionService;

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
			String name = request.getParameter("name");
			pagination.setPageSize(10);
			Map<String, Object> map = hqHessionService.queryHqAllMeeting(pagination, name);
			List<Map<String, String>> meetingList = (List<Map<String, String>>) map.get("meetingList");
			Map<String, String> _pagination = (Map<String, String>) map.get("pagination");
			pagination.setBegin(Integer.parseInt(_pagination.get("begin")));
			pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
			pagination.setEnd(Integer.parseInt(_pagination.get("end")));
			pagination.setCurrentPage(Integer.parseInt(_pagination.get("currentPage")));
			pagination.setCurrentUrl(request.getRequestURI());
			pagination.setPageSize(Integer.parseInt(_pagination.get("pageSize")));
			pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));
			pagination.setRequest(request);
			request.setAttribute("meetingList", meetingList);
			request.setAttribute("currentPath", request.getRequestURI());
			request.setAttribute("name", name);
			request.setAttribute("pagination", pagination);
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
	public String classroomListForSelect(HttpServletRequest request) {
		try {
			/*String whereSql = " status=1";
			Classroom classroom = new Classroom();
			whereSql += classroomBiz.addCondition(request, classroom);
			pagination.setCurrentUrl(classroomBiz.getCurrentUrl(request,
					pagination, classroom));
			List<Classroom> classroomList = classroomBiz.find(pagination,
					whereSql);
			List<ClassroomDTO> classroomDTOList=null;
			if(classroomList!=null&&classroomList.size()>0){
				classroomDTOList=new LinkedList<ClassroomDTO>();
				for(Classroom cr:classroomList){
					ClassroomDTO classroomDTO=new ClassroomDTO(cr);
					User user=userBiz.findById(cr.getCompereId());
					if(user!=null){
						classroomDTO.setCompereName(user.getName());
						classroomDTO.setCompereJob(user.getJob());
					}
					classroomDTOList.add(classroomDTO);
				}
			}
			request.setAttribute("classroomList", classroomDTOList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("classroom", classroom);*/
			String name = request.getParameter("name");
			request.setAttribute("name", name);
			//使用时间
			String useTime = request.getParameter("startTime");
			//归还时间
			String turnTime = request.getParameter("endTime");
			List<Map<String, String>> map = hqHessionService.queryMeetingListByTimeOrName(useTime, turnTime, name);
			String str = gson.toJson(map);
			List<Meeting> meetingList = gson.fromJson(str, new TypeToken<List<Meeting>>() {
			}.getType());
			request.setAttribute("meetingList", meetingList);
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

			String startTime = DateUtils.format(courseArrange.getStartTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
			String endTime = DateUtils.format(courseArrange.getEndTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
			request.setAttribute("startTime", startTime);
			request.setAttribute("endTime", endTime);

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
			User user=userBiz.findById(classroom.getCompereId());
			if(user!=null){
				request.setAttribute("compereName",user.getName());
			}
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
		if(classroom.getCompereId()==null||Long.valueOf(0).equals(classroom.getCompereId())){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请选择主持人",
					null);
		}
		return null;
	}
}
