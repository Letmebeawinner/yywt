package com.jiaowu.controller.research;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.research.ResearchBiz;
import com.jiaowu.entity.research.Research;
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
import java.util.List;
import java.util.Map;

/**
 * 调研报告类别Controller
 * @author 李帅雷
 *
 */
@Controller
public class ResearchController extends BaseController{
    
	private static final String ADMIN_PREFIX="/admin/jiaowu/research";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/research";
    private static Logger logger = LoggerFactory.getLogger(ResearchController.class);
    @Autowired
	private ResearchBiz researchBiz;
	
	@InitBinder({"research"})
	public void initXinDe(WebDataBinder binder){
		binder.setFieldDefaultPrefix("research.");
	}
	
	@InitBinder   
	protected  void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
     * @Description 跳转到创建调研报告类别的页面
     * @author 李帅雷
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateResearch")
	public String toCreateResearch(){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/research/create_research";
	}
	
	/**
     * @Description 创建调研报告类别
     * @param request
     * @param research
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createResearch")
	@ResponseBody
	public Map<String,Object> createResearch(HttpServletRequest request,@ModelAttribute("research")Research research){
		Map<String,Object> json=null;
		try{
			json=validateResearch(request,research);
			if(json!=null){
				return json;
			}
			researchBiz.save(research);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
	/**
	 * @Description 调研报告类别列表
	 * @param request
	 * @param pagination
	 * @return String
	 */
	@RequestMapping(ADMIN_PREFIX+"/researchList")
	public String researchList(HttpServletRequest request,
			                   @ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" status=1";
			Research research = new Research();
			String title = request.getParameter("title");
			if(!StringUtils.isTrimEmpty(title)){
                whereSql+=" and title like '%"+title+"%'";
                research.setTitle(title);
            }
			pagination.setRequest(request);
			List<Research> researchList = researchBiz.find(pagination,whereSql);
			request.setAttribute("researchList", researchList);
			request.setAttribute("research", research);
			request.setAttribute("pagination",pagination);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/admin/research/research_list";
	}
	
	/**
	 * 弹出框形式的调研报告类别列表
	 * @param request
	 * @param pagination
	 * @return String
	 */
	@RequestMapping(WITHOUT_ADMIN_PREFIX+"/researchListForSelect")
	public String researchListForSelect(HttpServletRequest request,
			                   @ModelAttribute("pagination") Pagination pagination){
		try{
			StringBuffer sb=request.getRequestURL().append("?pagination.currentPage="+pagination.getCurrentPage());
			String whereSql=" status=1";
			Research research = new Research();
			String title = request.getParameter("title");
			if(!StringUtils.isTrimEmpty(title)){
                whereSql += " and title like '%" + title + "%'";
                research.setTitle(title);
                sb.append("&title="+title);
            }
			pagination.setCurrentUrl(sb.toString());
			List<Research> researchList = researchBiz.find(pagination,whereSql);
			request.setAttribute("researchList", researchList);
			request.setAttribute("research", research);
			request.setAttribute("pagination",pagination);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/admin/research/research_list_forSelect";
	}
	
	/**
     * @Description 删除调研报告类别
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/deleteResearch")
	@ResponseBody
	public Map<String,Object> deleteResearch(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			Research research=new Research();
			research.setId(id);
			research.setStatus(0);
			researchBiz.update(research);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description 跳转到修改调研报告类别的页面
     * @param request
     * @param id
     * @return String
     */
	@RequestMapping(ADMIN_PREFIX+"/toUpdateResearch")
	public String toUpdateResearch(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			Research research=researchBiz.findById(id);
			request.setAttribute("research", research);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/research/updateResearch";
	}
	
	
	@RequestMapping(ADMIN_PREFIX+"/updateResearch")
	@ResponseBody
	public Map<String,Object> updateResearch(HttpServletRequest request,@ModelAttribute("research")Research research){
		Map<String,Object> json=null;
		try{
			json=validateResearch(request,research);
			if(json!=null){
				return json;
			}
			researchBiz.update(research);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
	 * 验证调研报告类别
	 * @param request
	 * @param research
	 * @return
	 */
	public Map<String,Object> validateResearch(HttpServletRequest request,Research research){
		if(StringUtils.isTrimEmpty(research.getTitle())){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空", null);
		}
		if(research.getStartTime()==null){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "开始时间不能为空", null);
		}
		if(research.getEndTime()==null){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "结束时间不能为空", null);
		}
		if(research.getStartTime().getTime()>=research.getEndTime().getTime()){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "开始时间不能大于结束时间", null);
		}
		return null;
	}
}
