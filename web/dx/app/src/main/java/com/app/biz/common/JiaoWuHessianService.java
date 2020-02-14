package com.app.biz.common;

import com.a_268.base.core.Pagination;

import java.util.Map;

/**
 * 教务Hessian
 * @author 李帅雷
 *
 */
public interface JiaoWuHessianService {
	/**
	 * 教师列表
	 * @param pagination
	 * @param whereSql
	 * @return
	 */
	public Map<String,Object> teacherList(Pagination pagination, String whereSql);
	
	/**
	 * @Description 根据ID获取教师
	 * @param id
	 * @return
	 */
	public Map<String,String> getTeacherById(Long id);
	
	/**
	 * 学员列表
	 * @param pagination
	 * @param whereSql
	 * @return
	 */
	public Map<String,Object> userList(Pagination pagination, String whereSql);
	
	/**
	 * @Description 根据ID获取学员
	 * @param id
	 * @return
	 */
	public Map<String,String> getUserById(Long id);
}
