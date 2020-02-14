package com.jiaowu.controller.research;

import java.text.SimpleDateFormat;
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
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.research.ResearchReportBiz;
import com.jiaowu.controller.xinde.XinDeController;
import com.jiaowu.entity.exam.ExamComment;
import com.jiaowu.entity.research.Research;
import com.jiaowu.entity.research.ResearchReport;

/**
 * 调研报告Controller
 * @author 李帅雷
 *
 */
@Controller
public class ResearchReportController extends BaseController{
      
	private static Logger logger = LoggerFactory.getLogger(ResearchReportController.class);
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/researchReport";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/researchReport";
	
	@InitBinder({"researchReport"})
	public void initResearchReport(WebDataBinder binder){
		binder.setFieldDefaultPrefix("researchReport.");
	}
	
	@InitBinder   
	protected  void initBinder(WebDataBinder binder) {       
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
	}
	
	@Autowired
	private ResearchReportBiz researchReportBiz;
	@Autowired
	private CommonBiz commonBiz;
	
	/**
     * @Description 跳转到创建调研报告的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateResearchReport")
	public String toCreateResearchReport(HttpServletRequest request){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/research/create_researchReport";
	}
	
	/**
     * @Description 创建调研报告
     * @param request
     * @param researchReport
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createResearchReport")
	@ResponseBody
	public Map<String,Object> createResearchReport(HttpServletRequest request,@ModelAttribute("researchReport")ResearchReport researchReport){
		Map<String,Object> json=null;
		try{
			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			if(userMap.get("userType").equals("1")){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有新建调研报告的权限!", null);
                return json;
			}else if(userMap.get("userType").equals("2")){
				researchReport.setType("teacher");
			}else if(userMap.get("userType").equals("3")){
				researchReport.setType("student");
			}
			json=validateResearchReport(request, researchReport);
			if(json!=null){
				return json;
			}
			researchReport.setPeopleId(Long.parseLong(userMap.get("linkId")));
			researchReport.setPeopleName(commonBiz.getCurrentUserName(request));
			researchReportBiz.save(researchReport);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
	/**
     * @Description 调研报告列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/researchReportList")
	public String researchReportList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" status=1";
			ResearchReport researchReport=new ResearchReport();
			whereSql+=researchReportBiz.addCondition(request, researchReport);
			pagination.setRequest(request);
			List<ResearchReport> researchReportList = researchReportBiz.find(pagination,whereSql);
			request.setAttribute("researchReportList", researchReportList);
			request.setAttribute("pagination",pagination);
			request.setAttribute("researchReport",researchReport);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/research/researchReport_list";
	}
	
	/**
     * @Description 某调研报告详情
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/queryResearchReport")
	public String queryResearchReport(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			ResearchReport researchReport=researchReportBiz.findById(id);
			request.setAttribute("researchReport", researchReport);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/research/query_researchReport";
	}
	
	/**
     * @Description 删除调研报告
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/deleteResearchReport")
	@ResponseBody
	public Map<String,Object> deleteResearchReport(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			ResearchReport researchReport=new ResearchReport();
			researchReport.setId(id);
			researchReport.setStatus(0);
			researchReportBiz.update(researchReport);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
	 * 验证调研报告
	 * @param request
	 * @param researchReport
	 * @return
	 */
	public Map<String,Object> validateResearchReport(HttpServletRequest request,ResearchReport researchReport){
		if(researchReport.getResearchId()==null||researchReport.getResearchId()<=0){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "调研报告类型不能为空", null);
        }
		/*if(StringUtils.isTrimEmpty(researchReport.getContent())){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空", null);
		}*/
		return null;
	}
}
