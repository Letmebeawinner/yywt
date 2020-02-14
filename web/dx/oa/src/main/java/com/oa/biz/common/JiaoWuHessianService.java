package com.oa.biz.common;

import com.a_268.base.core.Pagination;

import java.util.Map;

/**
 * 教务Hessian
 * @author ccl
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
	 * 学员列表
	 * @param pagination
	 * @param whereSql
	 * @return
	 */
	public Map<String,Object> userList(Pagination pagination, String whereSql);
}
