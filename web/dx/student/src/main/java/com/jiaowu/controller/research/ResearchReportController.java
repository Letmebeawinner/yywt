package com.jiaowu.controller.research;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.research.ResearchBiz;
import com.jiaowu.biz.research.ResearchReportBiz;
import com.jiaowu.common.ResultTypeConstants;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.research.Research;
import com.jiaowu.entity.research.ResearchReport;
import com.jiaowu.util.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 调研报告Controller
 * @author 李帅雷
 *
 */
@Controller
public class ResearchReportController extends BaseController{
      
	private static final String ADMIN_PREFIX="/admin/jiaowu/researchReport";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/researchReport";
	private static Logger logger = LoggerFactory.getLogger(ResearchReportController.class);
	@Autowired
	private ResearchReportBiz researchReportBiz;
	@Autowired
	private CommonBiz commonBiz;
	@Autowired
	private ClassesBiz classesBiz;
    @Autowired
    private ClassTypeBiz classTypeBiz;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ResearchBiz researchBiz;

	@InitBinder({"researchReport"})
	public void initResearchReport(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("researchReport.");
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
     * 新建调研报告
     * @param approvalDepartment 1:科研处 2:生态所
     * @return 查询班型班次
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateResearchReport")
	public ModelAndView toCreateResearchReport(Integer approvalDepartment) {
		ModelAndView mv = new ModelAndView("/admin/research/create_researchReport");
		try{
            // 1正常 0删除
            // 查询班型
            List<ClassType> classTypes = classTypeBiz.find(null, "status=1");
            // 查询班次
			List<Classes> classList = classesBiz.find(null, "status=1");
			// 调研报告类型
			List<Research> researchList = researchBiz.findAll();

			if (approvalDepartment == ResultTypeConstants.KE_YAN) {
				mv.addObject("resultTypeName", ResultTypeConstants.KE_YAN_NAME);
			}
			if (approvalDepartment == ResultTypeConstants.ZI_ZHENG) {
				mv.addObject("resultTypeName", ResultTypeConstants.ZI_ZHENG_NAME);
			}

			mv.addObject(researchList);
            mv.addObject("classTypes", classTypes);
			mv.addObject("classList", classList);
			mv.addObject("approvalDepartment", approvalDepartment);
		}catch(Exception e){
			logger.error("ResearchReportController.toCreateResearchReport", e);
		}
		return mv;
	}

    /**
     * 查询班次
     */
    @RequestMapping(ADMIN_PREFIX + "/findClassByType")
    @ResponseBody
    public Map<String, Object> findClassByType(@RequestParam("typeId") Integer typeId) {
        Map<String, Object> resultMap;
        try {
            List<Classes> classList = classesBiz.find(null, "status=1 and classTypeId = " + typeId);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, classList);
        } catch (Exception e) {
            logger.error("ResearchReportController.findClassByType", e);
            resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }

        return resultMap;
    }
	
	/**
     * @Description 创建调研报告
     * @param request
     * @param researchReport
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createResearchReport")
	@ResponseBody
	public Map<String, Object> createResearchReport(HttpServletRequest request,
													@ModelAttribute("researchReport") ResearchReport researchReport) {
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
//			researchReport.setPeopleId(Long.parseLong(userMap.get("linkId")));
			researchReport.setPeopleName(commonBiz.getCurrentUserName(request));
			researchReportBiz.save(researchReport);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, researchReport.getApprovalDepartment());
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}


	/**
	 * 调研报告列表
	 * @param pagination 分页
	 * @param researchReport 可能包含 approvalDepartment researchId researchName
	 * @param approvalDepartment 必传 用于区分科研/咨政
	 * @return ModelAndView
	 */
	@RequestMapping(ADMIN_PREFIX+"/researchReportList")
	public String researchReportList(Model model,
									 @ModelAttribute("pagination") Pagination pagination,
									 @ModelAttribute("researchReport") ResearchReport researchReport,
									 @RequestParam("approvalDepartment") Integer approvalDepartment) {
		try{
			String whereSql = GenerateSqlUtil.getSql(researchReport);
			whereSql += " and status=0";
			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			if(userMap.get("userType").equals("1")){
			}else if(userMap.get("userType").equals("2")){
				whereSql+=" and peopleId="+userMap.get("linkId")+" and type='teacher'";
			}else if(userMap.get("userType").equals("3")){
				whereSql+=" and peopleId="+userMap.get("linkId")+" and type='student'";
			}
            whereSql += " order by id desc";

			// 拆分科研列表和生态文明所列表
			pagination.setRequest(request);
			List<ResearchReport> researchReportList = researchReportBiz.find(pagination,whereSql);
			model.addAttribute("researchReportList", researchReportList);
			model.addAttribute("approvalDepartment", approvalDepartment);
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
			// 0:默认正常 1:删除
			researchReport.setStatus(1);
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
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "调研报告类型不能为空, 请选择", null);
		}
        if (StringUtils.isTrimEmpty(researchReport.getClassType())) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "班型不能为空,请重新填写", null);
        }
		if (StringUtils.isTrimEmpty(researchReport.getClassStr())) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "班次不能为空,请重新填写", null);
		}
		if (StringUtils.isTrimEmpty(researchReport.getAgroup())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "调研组不能为空,请重新填写", null);
		}
		if (StringUtils.isTrimEmpty(researchReport.getTeacher())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "指导老师不能为空,请重新填写", null);
		}
		if (StringUtils.isTrimEmpty(researchReport.getZbr())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "第一执笔人不能为空,请重新填写", null);
		}
		if (StringUtils.isTrimEmpty(researchReport.getParticipant())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "课题参与人员不能为空,请重新填写", null);
		}
		if (StringUtils.isTrimEmpty(researchReport.getContact())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "联系方式不能为空,请重新填写", null);
		}
		if(StringUtils.isTrimEmpty(researchReport.getContent())){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空,请重新填写", null);
		}
		if (StringUtils.isTrimEmpty(researchReport.getFileUrl())) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "文件地址不能为空,请重新填写", null);
		}
		return null;
	}
}
