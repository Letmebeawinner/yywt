package com.jiaowu.biz.classes;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.classes.ClassTypeDao;
import com.jiaowu.entity.classes.ClassType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ClassTypeBiz extends BaseBiz<ClassType, ClassTypeDao>{
	/**
	 * 增加课程类别的搜索条件
	 * @param request
	 * @param classType
	 * @return
	 */
	public String addCondition(HttpServletRequest request,ClassType classType){
		StringBuffer sb=new StringBuffer();
		String name=request.getParameter("name");
		if(!StringUtils.isTrimEmpty(name)){
			classType.setName(name);
			sb.append(" and name like '%"+name+"%'");
        }
		String description=request.getParameter("description");
		if(!StringUtils.isTrimEmpty(description)){
			classType.setDescription(description);
            sb.append(" and description like '%"+description+"%'");
        }
		return sb.toString();
	}
	
	/**
	 * 为request设置课程类别集合的属性
	 * @param request
	 */
	public void setAttributeClassTypeList(HttpServletRequest request){
		List<ClassType> classTypeList=find(null," status=1");

		request.setAttribute("classTypeList",classTypeList);
	}



	//根据id修改数据
	public void updateClassee(ClassType classType, Long classId){
		String whrerSql =" id="+classId;
		this.updateByStrWhere(new ClassType(),whrerSql);
	}

}
