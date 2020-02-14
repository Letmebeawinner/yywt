package com.jiaowu.controller.classes;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.BaseHessianService;
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
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;

/**
 * 班型Controller
 * @author 李帅雷
 *
 */
@Controller
public class ClassTypeController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(ClassTypeController.class);
	
	@Autowired
	private ClassTypeBiz classTypeBiz;
	@Autowired
	BaseHessianService baseHessianService;
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/classType";

	@InitBinder({"classType"})
	public void initClassType(WebDataBinder binder){
		binder.setFieldDefaultPrefix("classType.");
	}
	
	/**
     * @Description 跳转到创建班型的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/createClassType")
	public String createClassType(HttpServletRequest request){
		try{
			
		}catch(Exception e){
			logger.info("ClassTypeController.createClassType",e);
		}
		return "/admin/class/create_classtype";
	}
	
	/**
     * @Description 创建班型
     * @param request
     * @param classType
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/doCreateClassType")
	@ResponseBody
	public Map<String,Object> doCreateClassType(HttpServletRequest request,@ModelAttribute("classType")ClassType classType){
		Map<String,Object> json=null;
		try{
			json=validateClassType(classType);
			if(json!=null){
				return json;
			}
			classType.setStatus(1);
			classTypeBiz.save(classType);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			logger.info("ClassTypeController.doCreateClassType",e);
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
	
	/**
     * @Description 班型列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/classTypeList")
	public String classTypeList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);


			ClassType classType=new ClassType();
			String whereSql = " status=1";
			whereSql+=classTypeBiz.addCondition(request, classType);
			pagination.setRequest(request);
	        List<ClassType> classTypeList = classTypeBiz.find(pagination,whereSql);
	        request.setAttribute("classTypeList",classTypeList);
	        request.setAttribute("pagination",pagination);
	        request.setAttribute("classType",classType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/class/classtype_list";
	}
	
	/**
     * @Description 跳转到修改班型的页面
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toUpdateClassType")
	public String toUpdateClassType(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			ClassType classType=classTypeBiz.findById(id);
			request.setAttribute("classType",classType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/class/update_classtype";
	}
	
	/**
     * @Description 修改班型
     * @param request
     * @param classType
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/updateClassType")
	@ResponseBody
	public Map<String,Object> updateClassType(HttpServletRequest request,@ModelAttribute("classType")ClassType classType){
		Map<String,Object> json=null;
		try{
			json=validateClassType(classType);
			if(json!=null){
				return json;
			}
			classTypeBiz.update(classType);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description 删除班型
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/delClassType")
	@ResponseBody
	public Map<String,Object> delClassType(HttpServletRequest request,@RequestParam("id") String id){
		Map<String,Object> json=null;
		try{
			classTypeBiz.deleteById(Long.parseLong(id));
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
	 * 验证班型
	 * @param classType
	 * @return
	 */
	public Map<String,Object> validateClassType(ClassType classType){
		Map<String,Object> error=null;
		if(StringUtils.isTrimEmpty(classType.getName())){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空", null);
			return error;
		}
		return null;
	}
}
