/**
 * 
 */
package com.jiaowu.biz.courseType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.courseType.CourseTypeDao;
import com.jiaowu.entity.courseType.CourseType;

/**
 * @author 李帅雷
 *
 */
@Service
public class CourseTypeBiz extends BaseBiz<CourseType, CourseTypeDao>{
	/**
	 * 为request添加属性课程类别的集合
	 * @param request
	 */
	public void setAttributeCourseTypeList(HttpServletRequest request){
		List<CourseType> courseTypeList=find(null," status=1");
		request.setAttribute("courseTypeList", courseTypeList);
	}
	
	/**
	 * 增加课程类别的搜索条件
	 * @param request
	 * @param courseType
	 * @return
	 */
	public String addCondition(HttpServletRequest request,CourseType courseType){
		StringBuffer sb=new StringBuffer();
		if(!StringUtils.isTrimEmpty(courseType.getName())){
			sb.append(" and name like '%"+courseType.getName()+"%'");
		}
		return sb.toString();
	}
}
