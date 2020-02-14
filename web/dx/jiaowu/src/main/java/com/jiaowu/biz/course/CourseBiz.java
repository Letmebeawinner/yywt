package com.jiaowu.biz.course;

import javax.servlet.http.HttpServletRequest;

import com.jiaowu.biz.common.HrHessianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.course.CourseDao;
import com.jiaowu.entity.course.Course;
import com.jiaowu.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class CourseBiz extends BaseBiz<Course, CourseDao> {
	@Autowired
	private HrHessianService hrHessianService;
	/**
	 * 增加课程的搜索条件
	 * @param request
	 * @param course
	 * @return
	 */
	public String addCondition(HttpServletRequest request, Course course) {
		StringBuffer sb = new StringBuffer();
		String teacherId = request.getParameter("teacherId");
		if (!StringUtils.isTrimEmpty(teacherId)&&!teacherId.equals("0")) {
			sb.append(" and teacherId like concat('%'," + teacherId+",'%')");
			course.setTeacherId(teacherId);
			course.setTeacherName(getTeacherNamesByTeacherIds(teacherId));
		}
		String status = request.getParameter("status");
		if (!StringUtils.isTrimEmpty(status) && Integer.parseInt(status) > -1) {
			sb.append(" and status=" + status);
			course.setStatus(Integer.parseInt(status));
		}
		String courseTypeId = request.getParameter("courseTypeId");
		if (!StringUtils.isTrimEmpty(courseTypeId)
				&& Long.parseLong(courseTypeId) > 0) {
			sb.append(" and courseTypeId=" + courseTypeId);
			course.setCourseTypeId(Long.parseLong(courseTypeId));
		}
        String year = request.getParameter("year");
        if(!StringUtils.isTrimEmpty(year)){
            sb.append(" and year = "+year);
            course.setYear(year);
        }
        String season = request.getParameter("season");
        if(!StringUtils.isTrimEmpty(season)){
           sb.append(" and season ="+season);
           course.setSeason(season);
        }
		String keyWord = request.getParameter("keyWord");
		StringBuffer stringBuffer = new StringBuffer();
		if (!StringUtils.isTrimEmpty(keyWord)) {
            stringBuffer.append(" and (name like '%" + keyWord +"%'"+" or courseTypeName like '%" +keyWord+ "%'" + " or note like '%" +keyWord+ "%'");
			Map<String, Object> map = hrHessianService.getEmployeeListBySql(null, "status!=2 and ( type=2 or type=3 )");
			List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
			if(teacherList!=null&&teacherList.size()>0){
				for(Map<String,String> teacher:teacherList){
					if(teacher.get("name").indexOf(keyWord) >= 0){
                        stringBuffer.append(" or teacherId like '%" + teacher.get("id").toString() + "%'");
					}
				}
			}
            stringBuffer.append(")");
			request.setAttribute("keyWord",keyWord);
		}
		return sb.toString()+stringBuffer.toString();
	}

	/**
	 * 获取页面地址
	 * @param request
	 * @param pagination
	 * @param course
	 * @return
	 */
	public String getCurrentUrl(HttpServletRequest request,
			Pagination pagination, Course course) {
		StringBuffer sb = request.getRequestURL().append(
				"?pagination.currentPage=" + pagination.getCurrentPage());
		if(!StringUtils.isTrimEmpty(course.getTeacherId())) {
			sb.append("&teacherId=" + course.getTeacherId() + "&teacherName="
					+ course.getTeacherName());
		}
		if (course.getStatus() != null && course.getStatus() > -1) {
			sb.append("&status=" + course.getStatus());
		}
		if (StringUtil.biggerThanZero(course.getCourseTypeId())) {
			sb.append("&courseTypeId=" + course.getCourseTypeId());
		}
		return sb.toString();
	}

	/**
	 * 根据教师ID字符串获取教师名称
	 * @param teacherIds
	 * @return
     */
	public String getTeacherNamesByTeacherIds(String teacherIds){
		if(StringUtils.isTrimEmpty(teacherIds)){
			return "";
		}
		if(teacherIds.substring(teacherIds.length()-1,teacherIds.length()).equals(",")){
			teacherIds=teacherIds.substring(0,teacherIds.length()-1);
		}
		Map<String, Object> map = hrHessianService.getEmployeeListBySql(null, " id in ("+teacherIds+")");
		List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
		if(teacherList!=null&&teacherList.size()>0){
			StringBuilder sb=new StringBuilder();
			for(Map<String,String> teacher:teacherList){
				sb.append(teacher.get("name")+",");
			}
			return sb.substring(0,sb.length()-1).toString();
		}else{
			return "";
		}
	}










}
