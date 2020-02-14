package com.jiaowu.controller.meeting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.jiaowu.biz.meeting.MeetingBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.exam.ExamComment;
import com.jiaowu.entity.meeting.Meeting;

/**
 * 会议Controller
 * @author 李帅雷
 *
 */
@Controller
public class MeetingController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(ClassesController.class);
	
	@Autowired
	private MeetingBiz meetingBiz;
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/meeting";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/meeting";
	
	@InitBinder("meeting")
	public void initMeeting(WebDataBinder binder){
		binder.setFieldDefaultPrefix("meeting.");
	}
	
	@InitBinder   
	protected  void initBinder(WebDataBinder binder) {       
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
	}
	
	/**
     * @Description 跳转到创建会议的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateMeeting")
	public String toCreateMeeting(HttpServletRequest request){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/meeting/create_meeting";
	}
	
	/**
     * @Description 创建会议
     * @param request
     * @param meeting
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createMeeting")
	@ResponseBody
	public Map<String,Object> createMeeting(HttpServletRequest request,@ModelAttribute("meeting")Meeting meeting){
		Map<String,Object> json=null;
		try{
			/*tring errorInfo=meetingBiz.validatestartTimeAndEndTime(startTime,endTime);
			if(!StringUtils.isTrimEmpty(errorInfo)){
				return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
			}*/
			json=validateMeeting(request,meeting);
			if(json!=null){
				return json;
			}
			meetingBiz.save(meeting);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
	
	/**
     * @Description 会议列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/meetingList")
	public String meetingList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" status=1";
			Meeting meeting=new Meeting();
			whereSql+=meetingBiz.addCondition(request, meeting);
			pagination.setRequest(request);
	        List<Meeting> meetingList = meetingBiz.find(pagination,whereSql);
	        request.setAttribute("meetingList",meetingList);
	        request.setAttribute("pagination",pagination);
	        request.setAttribute("meeting",meeting);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/meeting/meeting_list";
	}
	
	/**
     * @Description 弹出框形式的会议列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(WITHOUT_ADMIN_PREFIX+"/meetingListForSelect")
	public String meetingListForSelect(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" status=1";
			Meeting meeting=new Meeting();
			whereSql=meetingBiz.addCondition(request, meeting);
			pagination.setCurrentUrl(meetingBiz.getCurrentUrl(request, pagination, meeting));
	        List<Meeting> meetingList = meetingBiz.find(pagination,whereSql);
	        request.setAttribute("meetingList",meetingList);
	        request.setAttribute("pagination",pagination);
	        request.setAttribute("meeting",meeting);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/meeting/meeting_list_forSelect";
	}
	
	
	/**
     * @Description 删除会议
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/deleteMeeting")
	@ResponseBody
	public Map<String,Object> deleteMeeting(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			Meeting meeting=new Meeting();
			meeting.setId(id);
			meeting.setStatus(0);
			meetingBiz.update(meeting);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description 跳转到修改会议的页面
     * @param request
     * @param id
     * @return String
     */
	@RequestMapping(ADMIN_PREFIX+"/toUpdateMeeting")
	public String toUpdateMeeting(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			Meeting meeting=meetingBiz.findById(id);
			request.setAttribute("meeting",meeting);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/meeting/update_meeting";
	}
	
	
	@RequestMapping(ADMIN_PREFIX+"/updateMeeting")
	@ResponseBody
	public Map<String,Object> updateMeeting(HttpServletRequest request,@ModelAttribute("meeting")Meeting meeting){
		Map<String,Object> json=null;
		try{
			json=validateMeeting(request, meeting);
			if(json!=null){
				return json;
			}
			meetingBiz.update(meeting);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
	 * 验证会议
	 * @param request
	 * @param meeting
	 * @return
	 */
	public Map<String,Object> validateMeeting(HttpServletRequest request,Meeting meeting){
		if(StringUtils.isTrimEmpty(meeting.getName())){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空", null);
		}
		if(meeting.getStartTime()==null){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "开始时间不能为空", null);
		}
		if(meeting.getEndTime()==null){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "结束时间不能为空", null);
		}
		if(meeting.getStartTime().getTime()>=meeting.getEndTime().getTime()){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "开始时间不能大于结束时间", null);
		}
		if(meeting.getStartTime().getTime()<new Date().getTime()){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "开始时间不能早于当前时间", null);
		}
		return null;
	}
}
