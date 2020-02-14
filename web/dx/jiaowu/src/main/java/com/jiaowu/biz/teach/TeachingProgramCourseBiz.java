package com.jiaowu.biz.teach;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.common.RegExpressionUtil;
import com.jiaowu.entity.classroom.Classroom;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class TeachingProgramCourseBiz extends
		BaseBiz<TeachingProgramCourse, TeachingProgramCourseDao> {
	@Autowired
	private CourseBiz courseBiz;
	@Autowired
	private TeachingInfoBiz teachingInfoBiz;
	@Autowired
	private ClassesBiz classesBiz;
	@Autowired
	private ClassroomBiz classroomBiz;

	/**
	 * 为teachingProgramCourse设置更多的信息
	 * 
	 * @param teachingProgramCourse
	 */
	public void setMoreInfo(TeachingProgramCourse teachingProgramCourse) {
		Course course = courseBiz.findById(teachingProgramCourse.getCourseId());
		teachingProgramCourse.setTotalHour(course.getHour());
//		teachingProgramCourse.setTeacherId(course.getTeacherId());
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
		if (!StringUtils.isTrimEmpty(teacherId)
				&& Long.parseLong(teacherId) > 0) {
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

	/**
	 * @Description 获得Hsscell内容
	 * @param cell
	 * @return
	 */
	public String getCellValue(HSSFCell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					DecimalFormat df = new DecimalFormat("0");
					value = df.format(cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue().trim();
					break;
				default:
					value = "";
					break;
			}
		}
		return value.trim();
	}

	/**
	 * 批量导入教学课程
	 * @param myFile
	 * @param request
	 * @return
	 * @throws Exception
     */
	public String batchImportTeachingProgramCourse(MultipartFile myFile, HttpServletRequest request) throws Exception{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher=null;
		Course rightCourse=null;
		Classes righClasses=null;
		Classroom rightClassroom=null;
		List<Course> courseList=courseBiz.find(null," status=1");
		if(courseList!=null&&courseList.size()>0){
			for(Course course:courseList){
				course.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(course.getTeacherId()));
			}
		}
		if(courseList==null||courseList.size()==0){
			return "当前没有可选课程";
		}
		List<Classes> classesList=classesBiz.find(null," status=1");
		if(classesList==null||classesList.size()==0){
			return "当前没有可选班次";
		}
		List<Classroom> classroomList=classroomBiz.find(null," status=1");
		if(classroomList==null||classroomList.size()==0){
			return "当前没有可选教室";
		}
		StringBuffer msg =new StringBuffer();
		// datalist拼装List<String[]> datalist,
		HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
		HSSFSheet sheet = wookbook.getSheetAt(0);

		int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
		for (int i = 1; i <rows+1; i++) {
			// 读取左上端单元格
			HSSFRow row = sheet.getRow(i);
			// 行不为空
			if (row != null) {
				String courseId = getCellValue(row.getCell((short) 0));//课程ID
				if(StringUtils.isTrimEmpty(courseId)){
					msg.append("第"+i+"行课程ID格式错误;");
					continue;
				}
				matcher=pattern.matcher(courseId);
				if(!matcher.matches()){
					msg.append("第"+i+"行课程ID格式错误;");
					continue;
				}
				boolean checkCoourse=false;
				Long courseIdLong=Long.parseLong(courseId);

				for(Course course:courseList){
					if(course.getId().equals(courseIdLong)){
						checkCoourse=true;
						rightCourse=course;
						break;
					}
				}
				if(!checkCoourse){
					msg.append("第"+i+"行没有该课程;");
					continue;
				}
				String classId = getCellValue(row.getCell((short) 1));//班次ID
				if(StringUtils.isTrimEmpty(classId)){
					msg.append("第"+i+"行班次ID格式错误;");
					continue;
				}
				matcher=pattern.matcher(classId);
				if(!matcher.matches()){
					msg.append("第"+i+"行班次ID格式错误;");
					continue;
				}
				boolean checkClass=false;
				Long classIdLong=Long.parseLong(classId);

				for(Classes classes:classesList){
					if(classes.getId().equals(classIdLong)){
						checkClass=true;
						righClasses=classes;
						break;
					}
				}
				if(!checkClass){
					msg.append("第"+i+"行没有该班次;");
					continue;
				}
				String classroomId = getCellValue(row.getCell((short) 2));//教室ID
				if(StringUtils.isTrimEmpty(classroomId)){
					msg.append("第"+i+"行教室ID格式错误;");
					continue;
				}
				matcher=pattern.matcher(classroomId);
				if(!matcher.matches()){
					msg.append("第"+i+"行教室ID格式错误;");
					continue;
				}
				boolean checkClassroom=false;
				Long classroomIdLong=Long.parseLong(classroomId);
				for(Classroom classroom:classroomList){
					if(classroom.getId().equals(classroomIdLong)){
						checkClassroom=true;
						rightClassroom=classroom;
						break;
					}
				}
				if(!checkClassroom){
					msg.append("第"+i+"行没有该教室;");
					continue;
				}

				String note = getCellValue(row.getCell((short) 3));//备注

				TeachingProgramCourse teachingProgramCourse=new TeachingProgramCourse(righClasses,rightClassroom,rightCourse);
				String errorInfo=checkTeachingProgramCourseIsDuplicated(teachingProgramCourse);
				if(!StringUtils.isTrimEmpty(errorInfo)){
					msg.append(errorInfo);
					continue;
				}
				save(teachingProgramCourse);
				this.addTeachingInfoOfAddTeachingProgramCourse(teachingProgramCourse);
			}
		}
//		updateClassStudentNum(classIdList,addNumList);
		return msg.toString();
	}

	/**
	 * 查询某班次是否已存在该教学计划课程
	 * @param teachingProgramCourse
	 * @return
	 */
	public String checkTeachingProgramCourseIsDuplicated(
			TeachingProgramCourse teachingProgramCourse) {
		String sql=" status=1 and classId="
				+ teachingProgramCourse.getClassId()
				+ " and courseId="
				+ teachingProgramCourse.getCourseId();
		if(teachingProgramCourse.getId()!=null){
			sql+=" and id!="+teachingProgramCourse.getId();
		}
		List<TeachingProgramCourse> teachingProgramCourseList = this
				.find(null,sql);
		if (teachingProgramCourseList != null
				&& teachingProgramCourseList.size() > 0) {
			return "该班次已存在"+teachingProgramCourse.getCourseName()+"课程,请重新选择班次或课程!";
		}
		return null;
	}
}
