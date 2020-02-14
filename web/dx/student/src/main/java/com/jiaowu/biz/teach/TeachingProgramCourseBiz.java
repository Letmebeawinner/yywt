package com.jiaowu.biz.teach;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teachingInfo.TeachingInfoBiz;
import com.jiaowu.dao.classes.ClassesDao;
import com.jiaowu.dao.teach.TeachingProgramCourseDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.teachingInfo.TeachingInfo;

@Service
public class TeachingProgramCourseBiz extends
		BaseBiz<TeachingProgramCourse, TeachingProgramCourseDao> {
	@Autowired
	private CourseBiz courseBiz;
	@Autowired
	private TeachingInfoBiz teachingInfoBiz;
	@Autowired
	private ClassesBiz classesBiz;

	/**
	 * 为teachingProgramCourse设置更多的信息
	 * 
	 * @param teachingProgramCourse
	 */
	public void setMoreInfo(TeachingProgramCourse teachingProgramCourse) {
		Course course = courseBiz.findById(teachingProgramCourse.getCourseId());
		teachingProgramCourse.setTotalHour(course.getHour());
		teachingProgramCourse.setTeacherId(Long.parseLong(course.getTeacherId().split(",")[0]));
//		teachingProgramCourse.setTeacherName(course.getTeacherName());
		teachingProgramCourse.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(course.getTeacherId()));
	}

	/**
	 * 增加教学动态,主题为新增教学计划课程
	 * 
	 * @param teachingProgramCourse
	 */
	public void addTeachingInfoOfAddTeachingProgramCourse(
			TeachingProgramCourse teachingProgramCourse) {
		TeachingInfo teachingInfo = new TeachingInfo();
		teachingInfo.setTitle(teachingProgramCourse.getClassName()
				+ "班次新增了一个教学计划课程");
		teachingInfo.setContent(teachingProgramCourse.getClassName() + "在"
				+ dateFormat(new Date()) + "新增了一条排课记录,课程为"
				+ teachingProgramCourse.getCourseName() + ",教室在"
				+ teachingProgramCourse.getClassroomName() + ",授课教师是"
				+ teachingProgramCourse.getTeacherName() + "。");
		teachingInfo.setType(2);
		teachingInfoBiz.save(teachingInfo);
	}

	public String dateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 增加教学计划课程的搜索条件
	 * 
	 * @param request
	 * @param teachingProgramCourse
	 * @return
	 */
	public String addCondition(HttpServletRequest request,
			TeachingProgramCourse teachingProgramCourse) {
		StringBuffer sb = new StringBuffer();
		String courseId = request.getParameter("courseId");
		String courseName = request.getParameter("courseName");
		if (!StringUtils.isTrimEmpty(courseId) && Long.parseLong(courseId) > 0) {
			sb.append(" and courseId=" + courseId);
			teachingProgramCourse.setCourseId(Long.parseLong(courseId));
			teachingProgramCourse.setCourseName(courseName);
		}
		String classId = request.getParameter("classId");
		String className = request.getParameter("className");
		if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
			sb.append(" and classId=" + classId);
			teachingProgramCourse.setClassId(Long.parseLong(classId));
			teachingProgramCourse.setClassName(className);
		}
		String teacherId = request.getParameter("teacherId");
		String teacherName = request.getParameter("teacherName");
		/*if (!StringUtils.isTrimEmpty(teacherId)
				&& Long.parseLong(teacherId) > 0) {
			sb.append(" and teacherId=" + teacherId);
			teachingProgramCourse.setTeacherId(Long.parseLong(teacherId));
			teachingProgramCourse.setTeacherName(teacherName);
		}*/
		if (!StringUtils.isTrimEmpty(teacherId)) {
			sb.append(" and teacherId=" + teacherId);
			teachingProgramCourse.setTeacherId(Long.parseLong(teacherId));
			teachingProgramCourse.setTeacherName(teacherName);
		}
		return sb.toString();
	}

	/**
	 * 为教学计划课程列表设置是否确定的属性
	 * 
	 * @param teachingProgramCourseList
	 */
	public void setConfirmTeachingProgram(
			List<TeachingProgramCourse> teachingProgramCourseList) {
		if (teachingProgramCourseList != null
				&& teachingProgramCourseList.size() > 0) {
			for (TeachingProgramCourse teachingProgramCourse2 : teachingProgramCourseList) {
				Classes classes = classesBiz.findById(teachingProgramCourse2
						.getClassId());
				if (classes != null) {
					teachingProgramCourse2.setConfirmTeachingProgram(classes
							.getConfirmTeachingProgram());
				} else {
					teachingProgramCourse2.setConfirmTeachingProgram(1);
				}
			}
		}
	}
	/**
	 * 增加一条教学动态,主题是更新了某教学计划课程
	 * @param teachingProgramCourse
	 */
	public void addTeachingInfoOfUpdateTeachingProgramCourse(
			TeachingProgramCourse teachingProgramCourse) {
		TeachingInfo teachingInfo = new TeachingInfo();
		teachingInfo.setTitle(teachingProgramCourse.getClassName()
				+ "班次更新了一个教学计划课程");
		teachingInfo.setContent(teachingProgramCourse.getClassName() + "在"
				+ dateFormat(new Date()) + "新增了一条排课记录,课程为"
				+ teachingProgramCourse.getCourseName() + ",教室在"
				+ teachingProgramCourse.getClassroomName() + ",授课教师是"
				+ teachingProgramCourse.getTeacherName() + "。");
		teachingInfo.setType(2);
		teachingInfoBiz.save(teachingInfo);
	}
	/**
	 * 增加一条教学动态,主题是删除了某教学计划课程
	 * @param teachingProgramCourse
	 */
	public void addTeachingInfoOfDeleteTeachingProgramCourse(TeachingProgramCourse teachingProgramCourse){
		// 添加教学动态
		TeachingInfo teachingInfo = new TeachingInfo();
		teachingInfo.setTitle(teachingProgramCourse.getClassName()
				+ "班次删除了一个教学计划课程");
		teachingInfo.setContent(teachingProgramCourse.getClassName()
				+ "在" + dateFormat(new Date()) + "删除了一条排课记录,课程为"
				+ teachingProgramCourse.getCourseName() + ",教室在"
				+ teachingProgramCourse.getClassroomName() + ",授课教师是"
				+ teachingProgramCourse.getTeacherName() + "。");
		teachingInfo.setType(2);
		teachingInfo.setClickTimes(0L);
		teachingInfo.setStatus(1);
		teachingInfoBiz.save(teachingInfo);
	}
}
