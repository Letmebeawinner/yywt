package com.jiaowu.controller.sign;

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
import com.jiaowu.biz.sign.SignBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.sign.Sign;

/**
 * 在线报名Controller
 * @author 李帅雷
 *
 */
@Controller
public class SignController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(SignController.class);
	
	@Autowired
	private SignBiz signBiz;
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/sign";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/sign";
	private static final String createSign="/admin/sign/create_sign";
	private static final String signList="/admin/sign/sign_list";
	
	@InitBinder({"sign"})
	public void initSign(WebDataBinder binder){
		binder.setFieldDefaultPrefix("sign.");
	}
	
	/**
     * @Description 跳转到在线报名的页面
     * @author 李帅雷
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateSign")
	public String toCreateSign(){
		return createSign;
	}
	
	/**
     * @Description 保存在线报名的信息
     * @param request
     * @param sign
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createSign")
	@ResponseBody
	public Map<String,Object> createSign(@RequestParam("sign")Sign sign){
		Map<String,Object> json=null;
		try{
			if(StringUtils.isTrimEmpty(sign.getName())){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空", null);
				return json;
			}
			if(StringUtils.isTrimEmpty(sign.getSex())){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "性别不能为空", null);
				return json;
			}
			if(StringUtils.isTrimEmpty(sign.getCompanyName())){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "企业名称不能为空", null);
				return json;
			}
			if(StringUtils.isTrimEmpty(sign.getMobile())){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "电话不能为空", null);
				return json;
			}
			if(StringUtils.isTrimEmpty(sign.getInfo())){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "科目信息不能为空", null);
				return json;
			}
			signBiz.save(sign);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description 在线报名列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/signList")
	public String signList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" 1=1";
			
			pagination.setRequest(request);
	        List<Sign> signList = signBiz.find(pagination,whereSql);
	        request.setAttribute("signList",signList);
	        request.setAttribute("pagination",pagination);
		}catch(Exception e){
			e.printStackTrace();
		}
		return signList;
	}
	

	 /**
     * @Description 删除某一条在线报名信息
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/delSign")
	@ResponseBody
	public Map<String,Object> delSign(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			signBiz.deleteById(id);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
}
