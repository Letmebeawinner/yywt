package com.jiaowu.biz.classroom;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.classroom.ClassroomDao;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.course.CourseArrange;

@Service
public class ClassroomBiz extends BaseBiz<Classroom, ClassroomDao> {
	/**
	 * 增加教室查询条件
	 * 
	 * @param request
	 * @param classroom
	 * @return
	 */
	public String addCondition(HttpServletRequest request, Classroom classroom) {
		StringBuffer sb = new StringBuffer();
		String position = request.getParameter("position");
		if (!StringUtils.isTrimEmpty(position)) {
			sb.append(" and position like '%" + position + "%'");
			classroom.setPosition(position);
		}
		return sb.toString();
	}
	
	/**
	 * 增加排课记录查询条件
	 * @param request
	 * @param courseArrange
	 * @return
	 */
	public String addCondition(HttpServletRequest request,
			CourseArrange courseArrange) {
		StringBuffer sb = new StringBuffer();
		try {
			SimpleDateFormat cstFormater = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = request.getParameter("startTime");
			if (!StringUtils.isTrimEmpty(startTime)) {
				if (startTime.contains("CST")) {
					startTime = sdf.format(cstFormater.parse(startTime));
				}
				sb.append(" and startTime > '" + startTime + "'");
				courseArrange.setStartTime(sdf.parse(startTime));
			}
			String endTime = request.getParameter("endTime");
			if (!StringUtils.isTrimEmpty(endTime)) {
				if (endTime.contains("CST")) {
					endTime = sdf.format(cstFormater.parse(endTime));
				}
				sb.append(" and endTime < '" + endTime + "'");
				courseArrange.setEndTime(sdf.parse(endTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 弹出框页面获取当前地址
	 * 
	 * @param request
	 * @param pagination
	 * @param classroom
	 * @return
	 */
	public String getCurrentUrl(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination,
			Classroom classroom) {
		StringBuffer sb = request.getRequestURL().append(
				"?pagination.currentPage=" + pagination.getCurrentPage());
		if (!StringUtils.isTrimEmpty(classroom.getPosition())) {
			sb.append("&position=" + classroom.getPosition());
		}
		return sb.toString();
	}
}
