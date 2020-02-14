package com.jiaowu.biz.meeting;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.meeting.MeetingDao;
import com.jiaowu.entity.meeting.Meeting;

@Service
public class MeetingBiz extends BaseBiz<Meeting, MeetingDao> {
	/**
	 * 增加会议搜索的条件
	 * @param request
	 * @param meeting
	 * @return
	 */
	public String addCondition(HttpServletRequest request, Meeting meeting) {
		StringBuffer sb = new StringBuffer();
		try {
			String name = request.getParameter("name");
			if (!StringUtils.isTrimEmpty(name)) {
				sb.append(" and name like '%" + name + "%'");
				meeting.setName(name);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = request.getParameter("startTime");
			if (!StringUtils.isTrimEmpty(startTime)) {
				sb.append(" and startTime > '" + startTime + "'");
				meeting.setStartTime(sdf.parse(startTime));
			}
			String endTime = request.getParameter("endTime");
			if (!StringUtils.isTrimEmpty(endTime)) {
				sb.append(" and endTime < '" + endTime + "'");
				meeting.setEndTime(sdf.parse(endTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 获取当前地址
	 * @param request
	 * @param pagination
	 * @param meeting
	 * @return
	 */
	public String getCurrentUrl(HttpServletRequest request,Pagination pagination,Meeting meeting){
		StringBuffer sb=request.getRequestURL().append("?pagination.currentPage="+pagination.getCurrentPage());
		if (!StringUtils.isTrimEmpty(meeting.getName())) {
			sb.append(" and name like '%" + meeting.getName() + "%'");
		}
		if (meeting.getStartTime()!=null) {
			sb.append(" and startTime > '" + meeting.getStartTime() + "'");
		}
		if (meeting.getEndTime()!=null) {
			sb.append(" and endTime < '" + meeting.getEndTime()!=null + "'");
		}
		return sb.toString();
	}
	
	/**
	 * 验证开始时间与结束时间
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String validatestartTimeAndEndTime(String startTime,String endTime){
		if(StringUtils.isTrimEmpty(startTime)){
			return "开始时间不能为空";
		}
		if(StringUtils.isTrimEmpty(endTime)){
			return "结束时间不能为空";
		}
		String format = "((19|20)[0-9]{2})/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01]) "
				+ "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(startTime);
		if(!matcher.matches()){
			return "开始时间格式不正确";
		}
		matcher = pattern.matcher(endTime);
		if(!matcher.matches()){
			return "结束时间格式不正确";
		}
		return null;
	}
}
