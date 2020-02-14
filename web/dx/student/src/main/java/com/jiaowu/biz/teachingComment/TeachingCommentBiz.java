package com.jiaowu.biz.teachingComment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.dao.teachingComment.TeachingCommentDao;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.enums.TeachingCommentType;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.teachingComment.TeachingComment;
import com.jiaowu.entity.teachingComment.TeachingCommentManagement;
import com.jiaowu.entity.user.User;

@Service
public class TeachingCommentBiz extends
		BaseBiz<TeachingComment, TeachingCommentDao> {

	@Autowired
	TeachingProgramCourseBiz teachingProgramCourseBiz;
	@Autowired
	CourseBiz courseBiz;
	@Autowired
	UserBiz userBiz;
	@Autowired
	TeachingCommentManagementBiz teachingCommentManagementBiz;

	public void autoAddTeachingComment() {
		List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz
				.find(null, " status=1");
		if (teachingProgramCourseList != null
				&& teachingProgramCourseList.size() > 0) {
			for (TeachingProgramCourse teachingProgramCourse : teachingProgramCourseList) {
				if (teachingProgramCourse.getLearnedHour() >= teachingProgramCourse
						.getTotalHour()) {
					List<TeachingComment> teachingCommentList = find(
							null,
							" teachingProgramCourseId="
									+ teachingProgramCourse.getId());
					checkTeachingComment(teachingProgramCourse,
							teachingCommentList);

				}
			}
		}
	}

	public void checkTeachingComment(
			TeachingProgramCourse teachingProgramCourse,
			List<TeachingComment> teachingCommentList) {
		if (teachingCommentList == null || teachingCommentList.size() == 0) {
			TeachingComment teachingComment = saveTeachingComment(teachingProgramCourse);
			saveTeachingCommentManageMent(teachingComment,
					teachingProgramCourse);
		}
	}

	public TeachingComment saveTeachingComment(
			TeachingProgramCourse teachingProgramCourse) {
		TeachingComment teachingComment = new TeachingComment();
		teachingComment.setTeachingProgramCourseId(teachingProgramCourse
				.getId());
		teachingComment.setCourseId(teachingProgramCourse.getCourseId());
		teachingComment.setCourseName(teachingProgramCourse.getCourseName());
		teachingComment.setClassId(teachingProgramCourse.getClassId());
		teachingComment.setClassName(teachingProgramCourse.getClassName());
		Date startTime = new Date();
		Date endTime = new Date();
		endTime.setDate(startTime.getDate() + 7);
		teachingComment.setStartTime(startTime);
		teachingComment.setEndTime(endTime);
		teachingComment.setStatus(1);
		save(teachingComment);
		return teachingComment;
	}

	public void saveTeachingCommentManageMent(TeachingComment teachingComment,
			TeachingProgramCourse teachingProgramCourse) {
		List<User> userList = userBiz.find(null, " classId="
				+ teachingProgramCourse.getClassId());
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				TeachingCommentManagement teachingCommentManagement = new TeachingCommentManagement();
				teachingCommentManagement.setTeachingCommentId(teachingComment
						.getId());
				teachingCommentManagement.setCourseId(teachingProgramCourse
						.getCourseId());
				teachingCommentManagement.setCourseName(teachingProgramCourse
						.getCourseName());
				teachingCommentManagement.setFromPeopleId(user.getId());
				teachingCommentManagement.setFromPeopleName(user.getName());
				teachingCommentManagement.setToPeopleId(teachingProgramCourse
						.getTeacherId());
				teachingCommentManagement.setToPeopleName(teachingProgramCourse
						.getTeacherName());
				teachingCommentManagement
						.setType(TeachingCommentType.student_to_teacher + "");
				teachingCommentManagement.setContent("");
				teachingCommentManagement.setStatus(2);
				teachingCommentManagementBiz.save(teachingCommentManagement);
			}
		}
	}
	
	/**
	 * 增加教学评价的搜索条件
	 * @param request
	 * @return
	 */
	public Map<String, Object> addCondition(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			StringBuffer condition = new StringBuffer();
			TeachingComment teachingComment = new TeachingComment();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = request.getParameter("startTime");
			if (!StringUtils.isTrimEmpty(startTime)) {
				condition.append(" and startTime > '" + startTime + "'");
				teachingComment.setStartTime(sdf.parse(startTime));
			}
			String endTime = request.getParameter("endTime");
			if (!StringUtils.isTrimEmpty(endTime)) {
				condition.append(" and endTime < '" + endTime + "'");
				teachingComment.setEndTime(sdf.parse(endTime));
			}
			result.put("condition", condition);
			result.put("teachingComment", teachingComment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 弹出框页面获取当前的路径
	 * @param request
	 * @param pagination
	 * @param teachingComment
	 * @return
	 */
	public String getCurrentUrl(HttpServletRequest request,
			Pagination pagination, TeachingComment teachingComment) {
		StringBuffer currentUrl = request.getRequestURL().append(
				"?pagination.currentPage=" + pagination.getCurrentPage());
		if (teachingComment.getStartTime() != null) {
			currentUrl.append("&startTime=" + teachingComment.getStartTime());
		}
		if (teachingComment.getEndTime() != null) {
			currentUrl.append("&endTime=" + teachingComment.getEndTime());
		}
		return currentUrl.toString();
	}
	
	
	public String getTeachingProgramCourseIds(List<TeachingProgramCourse> teachingProgramCourseList){
		StringBuffer sb=new StringBuffer();
		sb.append("(");
		for(TeachingProgramCourse teachingProgramCourse:teachingProgramCourseList){
			sb.append(teachingProgramCourse.getId()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}
}
