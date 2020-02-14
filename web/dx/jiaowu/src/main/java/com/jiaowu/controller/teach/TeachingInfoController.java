package com.jiaowu.controller.teach;

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
import com.jiaowu.biz.common.JiaoWuHessianBiz;
import com.jiaowu.biz.teachingInfo.TeachingInfoBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.teachingInfo.TeachingInfo;

/**
 * 教学动态Controller
 * @author 李帅雷
 *
 */
@Controller
public class TeachingInfoController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(TeachingInfoController.class);
	
	@Autowired
	private TeachingInfoBiz teachingInfoBiz;
	@Autowired
	private JiaoWuHessianBiz jiaoWuHessianBiz;
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/teachingInfo";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/teachingInfo";
	
	@InitBinder({"teachingInfo"})
	public void initTeachingInfo(WebDataBinder binder){
		binder.setFieldDefaultPrefix("teachingInfo.");
	}
	@InitBinder   
	protected  void initBinder(WebDataBinder binder) {       
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
	}
	
	/**
     * @Description 教学动态列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/teachingInfoList")
	public String teachingInfoList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			
			String whereSql=" status=1";
			TeachingInfo teachingInfo=new TeachingInfo();
			String title=request.getParameter("title");
			if(!StringUtils.isTrimEmpty(title)){
                whereSql+=" and title like '%"+title+"%'";
                teachingInfo.setTitle(title);
            }
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime=request.getParameter("startTime");
			if(!StringUtils.isTrimEmpty(startTime)){
                whereSql+=" and createTime > '"+startTime+"'";
                request.setAttribute("startTime",sdf.parse(startTime));
            }
			String endTime=request.getParameter("endTime");
			if(!StringUtils.isTrimEmpty(endTime)){
                whereSql+=" and createTime < '"+endTime+"'";
                request.setAttribute("endTime", sdf.parse(endTime));
            }
			pagination.setRequest(request);
			List<TeachingInfo> teachingInfoList=teachingInfoBiz.find(pagination,whereSql);
			request.setAttribute("teachingInfoList", teachingInfoList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("teachingInfo", teachingInfo);
		}catch(Exception e){
			logger.info("TeachingInfoController.teachingInfoList",e);
			return this.setErrorPath(request,e);
		}
		return "/admin/teachingInfo/teaching_info_list";
	}
	
	
	@RequestMapping(ADMIN_PREFIX+"/delTeachingInfo")
	@ResponseBody
	public Map<String,Object> delTeachingInfo(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			TeachingInfo teachingInfo=new TeachingInfo();
			teachingInfo.setId(id);
			teachingInfo.setStatus(0);
			teachingInfoBiz.update(teachingInfo);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			logger.info("TeachingInfoController.delTeachingInfo",e);
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
}
