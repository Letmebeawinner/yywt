/**
 * 
 */
package com.jiaowu.biz.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.a_268.base.util.SysUserUtils;
import com.jiaowu.entity.enums.UserType;

/**
 * @author Administrator
 *
 */
public interface CommonService {
	
	
	public UserType getCurrentUserType(HttpServletRequest request);
	/**
	 * 获取当前登录人的ID,根据base项目中userType的不同,这个ID有不同含义.1为领导,2为教师,3为学员.
	 * @param request
	 * @return
	 */
	public Long getCurrentUserId(HttpServletRequest request);
	
	/**
	 * 获取当前登录人的名称
	 * @param request
	 * @return
	 */
	public String getCurrentUserName(HttpServletRequest request);
	
	/**
	 * 获取当前登录人的用户类型
	 * @param request
	 * @return
	 */
	public Integer getCurrentUserTypeReturnString(HttpServletRequest request);
}
