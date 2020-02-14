package com.jiaowu.controller.exam;

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
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.exam.ExamCommentBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.xinde.XinDeBiz;
import com.jiaowu.controller.xinde.XinDeController;
import com.jiaowu.entity.exam.ExamComment;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.xinde.XinDe;

/**
 * 考评Controller
 * @author 李帅雷
 *
 */
@Controller
public class ExamController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(ExamController.class);
	
	@InitBinder({"examComment"})
	public void initExamComment(WebDataBinder binder){
		binder.setFieldDefaultPrefix("examComment.");
	}
	
	@Autowired
	private ExamCommentBiz examCommentBiz;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private HrHessianService hrHessianService;
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/examComment";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/examComment";
	
	/**
     * @Description 跳转到新建考评的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateExamComment")
	public String toCreateExamComment(HttpServletRequest request){
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/examComment/create_examComment";
	}
	
	/**
     * @Description 新建考评
     * @param request
     * @param examComment
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createExamComment")
	@ResponseBody
	public Map<String,Object> createExamComment(HttpServletRequest request,@ModelAttribute("examComment")ExamComment examComment){
		Map<String,Object> json=null;
		try{
			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			json=validateExamComment(userMap,examComment);
			if(json!=null){
				return json;
			}
			Long teacherId=Long.parseLong(userMap.get("linkId"));
			Map<String,String> teacherMap=hrHessianService.queryEmployeeById(teacherId);
			examComment.setFromPeopleId(teacherId);
			examComment.setFromPeopleName(teacherMap.get("name"));
			examCommentBiz.save(examComment);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
	
	/**
     * @Description 考评列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/examCommentList")
	public String examCommentList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			String whereSql=" status=1";
			if(userMap.get("userType").equals("2")){
				whereSql+=" and fromPeopleId="+userMap.get("linkId");
			}
			ExamComment examComment=new ExamComment();
			whereSql+=examCommentBiz.addCondition(request, examComment);
			pagination.setRequest(request);
	        List<ExamComment> examCommentList = examCommentBiz.find(pagination,whereSql);
	        request.setAttribute("examCommentList",examCommentList);
	        request.setAttribute("pagination",pagination);
	        request.setAttribute("examComment",examComment);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/examComment/examComment_list";
	}
	
	/**
     * @Description 查询某考评详情
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/queryExamComment")
	public String queryExamComment(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			ExamComment examComment=examCommentBiz.findById(id);
			request.setAttribute("examComment", examComment);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/examComment/query_examComment";
	}
	
	
	/**
     * @Description 删除考评
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/deleteExamComment")
	@ResponseBody
	public Map<String,Object> deleteExamComment(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			ExamComment examComment=new ExamComment();
			examComment.setId(id);
			examComment.setStatus(0);
			examCommentBiz.update(examComment);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
	 * 验证考评
	 * @param userMap
	 * @param examComment
	 * @return
	 */
	public Map<String,Object> validateExamComment(Map<String,String> userMap,ExamComment examComment){
		if(!userMap.get("userType").equals("2")){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "只有教师拥有新建考评的权限!", null);
		}
		if(StringUtils.isTrimEmpty(examComment.getContent())){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空", null);
		}
		return null;
	}
}
