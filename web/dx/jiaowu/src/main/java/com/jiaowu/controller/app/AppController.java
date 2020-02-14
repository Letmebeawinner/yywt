package com.jiaowu.controller.app;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.teachingInfo.TeachingInfoBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.user.User;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * APPController
 * @author 李帅雷
 *
 */
@Controller
@RequestMapping("/api/jiaowu")
public class AppController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private CourseArrangeBiz courseArrangeBiz;
	@Autowired
	private TeachingProgramCourseBiz teachingProgramCourseBiz;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private ClassesBiz classesBiz;
	@Autowired
	private ClassroomBiz classroomBiz;
	@Autowired
	private TeachingInfoBiz teachingInfoBiz;
	@Autowired
	private CourseBiz courseBiz;
	@Autowired
	private ClassTypeBiz classTypeBiz;
	@Autowired
	private HrHessianService hrHessianService;
	
	/**
	 * @Description ajax分页获取学员课表的内容
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/course/appStudentCourseArrange")
	@ResponseBody
	public Map<String,Object> appStudentCourseArrange(HttpServletRequest request,@RequestParam("userId")Long userId,@ModelAttribute("pagination") Pagination pagination){
		Map<String,Object> json=null;
		try{
			User user=userBiz.findById(userId);
			List<CourseArrange> courseArrangeList=null;
			if(user!=null){
				courseArrangeList=courseArrangeBiz.find(pagination," classId="+user.getClassId());
				classesBiz.setCourseInfoAndDeleteUselessCourseArrange(courseArrangeList);
			}

			if (CollectionUtils.isEmpty(courseArrangeList)) {
				courseArrangeList = Collections.emptyList();
			}

//			List<Course> courseArrangeList=courseBiz.find(pagination," 1=1 and status=1");

			Map<String,Object> map=new HashMap<String,Object>();
			map.put("courseArrangeList", courseArrangeList);
			map.put("pagination", pagination);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, map);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
}
