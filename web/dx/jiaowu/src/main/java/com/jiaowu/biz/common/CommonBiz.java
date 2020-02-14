/**
 * 
 */
package com.jiaowu.biz.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.enums.UserType;
import com.jiaowu.entity.user.User;

/**
 * @author Administrator
 *
 */
@Service
public class CommonBiz implements CommonService{

	@Autowired
	private HrHessianService hrHessianService;
	@Autowired
	private UserBiz userBiz;
	
	public UserType getCurrentUserType(HttpServletRequest request){
		Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
		if(userMap.get("userType").equals("3")){
			return UserType.student;
		}else if(userMap.get("userType").equals("2")){
			return UserType.teacher;
		}else{
			return UserType.leader;
		}
	}
	
	/**
	 * 获取当前登录人的ID,根据base项目中userType的不同,这个ID有不同含义.
	 * @param request
	 * @return
	 */
	public Long getCurrentUserId(HttpServletRequest request){
		Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
		String userId=userMap.get("linkId");
		if(StringUtils.isTrimEmpty(userId)){
			return 0L;
		}else{
			return Long.parseLong(userId);
		}
	}
	
	/**
	 * 获取当前登录人的名称
	 * @param request
	 * @return
	 */
	public String getCurrentUserName(HttpServletRequest request){
		Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
		if(userMap.get("userType").equals("1")){
			return userMap.get("userName");
		}else if(userMap.get("userType").equals("2")){
			Map<String,String> teacherMap=hrHessianService.queryEmployeeById(Long.parseLong(userMap.get("linkId")));
			return teacherMap.get("name");
		}else if(userMap.get("userType").equals("3")){
			User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
			return user.getName();
		}
		return null;
	}
	
	/**
	 * 获取当前登录人的用户类型
	 * @param request
	 * @return
	 */
	public Integer getCurrentUserTypeReturnString(HttpServletRequest request){
		Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
		return Integer.parseInt(userMap.get("userType"));
	}
}
