package com.houqin.biz.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 教务Hessian
 * @author ccl
 *
 */
public interface JiaoWuHessianService {

	/**
	 * 学员列表
	 * @param pagination
	 * @param whereSql
	 * @return
	 */
	public Map<String,Object> userList(Pagination pagination,String whereSql);

	/**
	 * @Description 根据ID获取学员
	 * @param id
	 * @return
	 */
	public Map<String,String> getUserById(Long id);

	/**
	 * @Description 获取最新的教学动态
	 * @param num
	 * @return
	 */
	public List<Map<String,String>> getLatestTeachingInfo(Long num);

	/**
	 * @Description 查询教学动态详情
	 * @param id
	 * @return
	 */
	public Map<String,String> getTeachingInfoById(Long id);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public Map<String,String> queryClassNameById(Long id);

	/**
	 * 教学动态列表
	 * @param pagination
	 * @param whereSql
	 * @return
	 */
	public Map<String,Object> teachingInfoList(Pagination pagination,String whereSql);

	/**
	 * 获取点击量最高的教学动态
	 * @param num
	 * @return
	 */
	public List<Map<String,String>> hotTeachingInfoList(Long num);

	/**
	 * 增加教学动态的点击量
	 * @param id
	 */
	public void addClickTimes(Long id);

	/**
	 * 根据学员ID集合获取学员信息
	 * @param studentIds
	 * @return
	 */
	public List<Map<String,String>> queryStudentByIds(List<Long> studentIds);

	/**
	 * 获取班次列表
	 *
	 * @param pagination
	 * @param sql
	 * @return
	 */
	Map<String, Object> queryClassesList(Pagination pagination, String sql);


	/**
	 * 查询班型类型
	 *
	 * @return {@code List<String>} 班次类型集合
	 * @since 2017-02-28
	 */
	Map<String, Object> listClassType();

	/**
	 * 查询班次
	 */
	Map<String,Object> listClasses();

	/**
	 * 修改学员信息
	 * @param userInfoMap
	 * @return
	 */
	public String updateUserInfo(Map<String, Object> userInfoMap);

	/**
	 * @param id
	 * @return
	 * @Description 查询班次信息
	 */
	public Map<String, String> getClassesById(Long id);



}
