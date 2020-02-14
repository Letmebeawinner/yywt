package com.menhu.biz.common;


import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 教务Hessian
 */
public interface JiaoWuHessianService {

	/**
	 * @Description 获取最新的教学动态
	 */
	public List<Map<String,String>> getLatestTeachingInfo(Long num);

	/**
	 * @Description 查询教学动态详情
	 */
	public Map<String,String> getTeachingInfoById(Long id);

	/**
	 * 教学动态列表
	 */
	public Map<String,Object> teachingInfoList(Pagination  pagination, String whereSql);

	/**
	 * 获取点击量最高的教学动态
	 */
	public List<Map<String,String>> hotTeachingInfoList(Long num);

	/**
	 * 增加教学动态的点击量
	 */
	public void addClickTimes(Long id);
}
