package com.a_268.base.controller;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.a_268.base.util.WebUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller层的基类，所有Controller类必须继承自此类，该类不能直接使用。
 * <p>
 * 		将Controller层一些通用的操作给抽离出来，封装到此类中，其他Controller类必须继承此类，子类可以直接使用此类中的方法。<br/>
 * 		子类在需要使用Controller对象的地方，直接调用this.<i>method()</i>
 * </p>
 * @author s.li
 */
public abstract class BaseController {

	/**
	 * JSON工具类
	 */
	protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * Redis缓存操作工具类
	 */
	protected static RedisCache redisCache = RedisCache.getInstance();

	@InitBinder({"pagination"})
	public void initPageBinder(WebDataBinder binder){
		binder.setFieldDefaultPrefix("pagination.");
	}
	
	/**
	 * JSON数据封装方法
	 * @param code 结果码
	 * @param message 描述信息
	 * @param data 数据
	 * @return Map<String,Object>
	 */
	protected Map<String,Object> resultJson(String code,String message,Object data){
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("code", code);
		resultMap.put("message", message);
		resultMap.put("data", data);
		return resultMap;
	}

	/**
	 * 设置错误显示页面
	 * @param request HttpServletRequest
	 * @param e Exception
	 * @return 页面URL
	 */
	protected String setErrorPath(HttpServletRequest request,Exception e){
		request.setAttribute("e",e);
		return "/common/error";
	}

	/**
	 * 设置列表重定向URL
	 * @param request HttpServletRequest
	 * @param clazz 当前Controller的Class
	 * @throws Exception
	 */
	protected void setRedirect(HttpServletRequest request,Class<?> clazz) throws Exception{
		String redirectName = getRedirectName(request,clazz);
		String rui = request.getRequestURI();

		Enumeration<String> enumer = request.getParameterNames();
		StringBuffer value = new StringBuffer(rui + "?");
		while (enumer.hasMoreElements()) {
			String name = enumer.nextElement();
			String val = request.getParameter(name);
			value.append(name + "=" + URLEncoder.encode(val, "UTF-8") + "&");
		}
		redisCache.set(redirectName,value.toString(),10*60);
	}

	/**
	 * 获取列表存在放在缓存中重定向URL
	 * @param request HttpServletRequest
	 * @param clazz 当前Controller的Class
	 * @return URL
	 */
	protected String getRedirectURI(HttpServletRequest request,Class<?> clazz){
		String redirectName = this.getRedirectName(request,clazz);
		String uri = (String)redisCache.get(redirectName);
		if(!StringUtils.isTrimEmpty(uri)){
			return "redirect:"+uri;
		}else{
			return null;
		}
	}

	/**
	 * 获取redirectName
	 * @param request HttpServletRequest
	 * @param clazz
	 * @return 名字
	 */
	private String getRedirectName(HttpServletRequest request,Class<?> clazz){
		String sid = WebUtils.getCookie(request,BaseCommonConstants.LOGIN_KEY);
		Long userId = SysUserUtils.getLoginSysUserId(request);
		return clazz.getCanonicalName().replaceAll("\\.", "_")+sid+userId;
	}

}
