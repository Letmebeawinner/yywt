package com.jiaowu.controller.course;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.common.FileExportImportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.teach.TeachingTaskBiz;
import com.jiaowu.biz.teachingInfo.TeachingInfoBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.course.IntelliCourseInfo;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.teach.TeachingTask;
import com.jiaowu.entity.teachingInfo.TeachingInfo;
import com.jiaowu.entity.user.User;

/**
 * 排课Controller
 * @author 李帅雷
 *
 */
@Controller
public class CourseArrangeController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(CourseArrangeController.class);

    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private ClassroomBiz classroomBiz;
    @Autowired
    private TeachingInfoBiz teachingInfoBiz;
    @Autowired
    private CourseBiz courseBiz;
    @Autowired
    private TeachingTaskBiz teachingTaskBiz;

    private static final String ADMIN_PREFIX="/admin/jiaowu/courseArrange";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/courseArrange";
    private static final String updateCourseArrange="/admin/courseArrange/updateCourseArrange";
    //	private static final String oneClassCourseArrange="/admin/courseArrange/one_class_course_arrange";
    private static final String classCourseArrange="/admin/courseArrange/class_course_arrange";
    private static final String teacherActivity="/admin/courseArrange/teacher_activity";
    private static final String teacherCourseArrange="/admin/courseArrange/teacher_course_arrange";
    private static final String classroomCourseArrange="/admin/courseArrange/classroom_course_arrange";
    private static final String studentCourseArrange="/admin/courseArrange/student_course_arrange";
    private static final String schoolCourseArrange="/admin/courseArrange/school_course_arrange";
    private static final String updateOneCourseArrange="/admin/courseArrange/update_one_course_arrange";
    @InitBinder({"courseArrange"})
    public void initCourseArrange(WebDataBinder binder){
        binder.setFieldDefaultPrefix("courseArrange.");
    }

    @InitBinder
    protected  void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * @Description 获取一条排课记录的时间,即学时
     * @param courseArrange
     * @return
     */
    public float getHour(CourseArrange courseArrange){
        int time=Integer.parseInt(courseArrange.getEndTime().getTime()-courseArrange.getStartTime().getTime()+"");
        return time*1.0f/(1000*60*60);
    }

    /**
     * @Description 为一个班次排课
     * @param request
     * @param classId
     * @return java.util.String
     */
    @RequestMapping(ADMIN_PREFIX+"/toUpdateCourseArrange")
    public String toUpdateCourseArrange(HttpServletRequest request,@RequestParam("classId")Long classId){
        try{
            String whereSql = " classId="+classId;
            //获取教学计划课程列表
            List<TeachingProgramCourse> teachingProgramCourseList=teachingProgramCourseBiz.find(null,whereSql);
            request.setAttribute("teachingProgramCourseList", teachingProgramCourseList);
            request.setAttribute("classId", classId);
            List<TeachingTask> teachingTaskList=teachingTaskBiz.find(null," classId="+classId);
            if(teachingTaskList!=null&&teachingTaskList.size()>0){
                request.setAttribute("teachingTask",teachingTaskList.get(0));
            }
        }catch(Exception e){
            logger.info("CourseArrangeController.toUpdateCourseArrange",e);
        }
        return updateCourseArrange;
    }

    /**
     * @Description 智能排课
     * @param request
     * @param classId
     * @return String
     */
    @RequestMapping(ADMIN_PREFIX+"/intelligentCourseArrange")
    public String intelligentCourseArrange(HttpServletRequest request,@RequestParam("classId")Long classId){
        try{
            //这里可以设为学期开始的第一天
            Date date=new Date();
            date.setHours(8);
            date.setMinutes(0);
            date.setSeconds(0);
            //所有的智能排课信息集合
            List<List<IntelliCourseInfo>> allIntelliCourseInfoList=new ArrayList<List<IntelliCourseInfo>>();
            //课程一次上课时间默认为1.5个小时,以下为课程上课次数集合.
            List<Integer> courseTimesList=new ArrayList<Integer>();
            //获取教学计划课程列表
            List<TeachingProgramCourse> teachingProgramCourseList=teachingProgramCourseBiz.find(null," classId="+classId +" and status = 1");
            if(teachingProgramCourseList!=null&&teachingProgramCourseList.size()>0){
                for(int i=0;i<teachingProgramCourseList.size();i++){
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseList.get(i);
                    Course course=courseBiz.findById(teachingProgramCourseList.get(i).getCourseId());
                    course.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(course.getTeacherId()));
                    //根据课程的学时来确定要上几节课,默认一节课1.5小时.
                    Integer courseTimes=(int)(course.getHour()/1.5);
                    Float leftHour=null;
                    if((courseTimes*1.5)<course.getHour()){
                        leftHour=(float)(course.getHour()-courseTimes*1.5);
                    }
                    if(leftHour!=null&&leftHour>0){
                        courseTimesList.add(courseTimes+1);
                    }else{
                        courseTimesList.add(courseTimes);
                    }
                    //一个教学计划课程的排课信息集合
                    List<IntelliCourseInfo> intelliCourseInfoList=new ArrayList<IntelliCourseInfo>();
                    for(int j=0;j<courseTimes;j++){
                        IntelliCourseInfo intelliCourseInfo=new IntelliCourseInfo();
                        intelliCourseInfo.setTeachingProgramCourseId(teachingProgramCourseList.get(i).getId());
                        intelliCourseInfo.setCourseId(teachingProgramCourseList.get(i).getCourseId());
                        intelliCourseInfo.setDuration(1.5F);
                        intelliCourseInfoList.add(intelliCourseInfo);
                    }
                    if(leftHour!=null&&leftHour>0){
                        IntelliCourseInfo intelliCourseInfo=new IntelliCourseInfo();
                        intelliCourseInfo.setTeachingProgramCourseId(teachingProgramCourseList.get(i).getId());
                        intelliCourseInfo.setCourseId(teachingProgramCourseList.get(i).getCourseId());
                        intelliCourseInfo.setDuration(getDuration(leftHour));
                        intelliCourseInfoList.add(intelliCourseInfo);
                    }
                    allIntelliCourseInfoList.add(intelliCourseInfoList);

                    //获取一个学期所有的上课有效时间
                    List<Map<String,Date>> termAvalibleTimeList=getTermAvalibleTimeList(date);
                    //获取一门教学计划课程在一学期的所有空余上课时间
                    List<Map<String,Date>> avaliableTimeList=getAvaliableTimeOfOneTeachingProgramCourse(termAvalibleTimeList,teachingProgramCourseList.get(i));
					/*if(intelliCourseInfoList!=null&&intelliCourseInfoList.size()>0&&avaliableTimeList!=null&&avaliableTimeList.size()>0){
						for(int j=0;j<intelliCourseInfoList.size();j++){
							int index=-1;
							for(int k=0;k<avaliableTimeList.size();k++){
								if((avaliableTimeList.get(k).get("endTime").getTime()-avaliableTimeList.get(k).get("startTime").getTime())>intelliCourseInfoList.get(j).getDuration()*60*60*1000){
									index=k;
									intelliCourseInfoList.get(j).setStartTime(avaliableTimeList.get(k).get("startTime"));
									Date endTime=new Date(avaliableTimeList.get(k).get("startTime").getTime()+(long)(intelliCourseInfoList.get(j).getDuration()*60*60*1000));
									intelliCourseInfoList.get(j).setEndTime(endTime);
									break;
								}
							}
							if(index!=-1){
								avaliableTimeList.remove(index);
							}
						}
					}*/
                    if(intelliCourseInfoList!=null&&intelliCourseInfoList.size()>0&&avaliableTimeList!=null&&avaliableTimeList.size()>0){
                        for(int j=0;j<intelliCourseInfoList.size();j++){
                            //可供上课的时间Map在list的下标
                            int index=-1;
                            //因为课程要尽量分的均匀,这是获取在有效时间列表尽量分布均匀的下标
                            int intervalIndex=avaliableTimeList.size()*j/intelliCourseInfoList.size();

                            for(int k=intervalIndex;k<avaliableTimeList.size();k++){
                                if((avaliableTimeList.get(k).get("endTime").getTime()-avaliableTimeList.get(k).get("startTime").getTime())>intelliCourseInfoList.get(j).getDuration()*60*60*1000){
                                    index=k;
                                    intelliCourseInfoList.get(j).setStartTime(avaliableTimeList.get(k).get("startTime"));
                                    Date endTime=new Date(avaliableTimeList.get(k).get("startTime").getTime()+(long)(intelliCourseInfoList.get(j).getDuration()*60*60*1000));
                                    intelliCourseInfoList.get(j).setEndTime(endTime);
                                    break;
                                }
                            }
                            //如果之前已经找到了时间可用于上课,则不用从头开始找.如果没有,从头开始找.
                            if(index==-1){
                                for(int k=0;k<avaliableTimeList.size();k++){
                                    if((avaliableTimeList.get(k).get("endTime").getTime()-avaliableTimeList.get(k).get("startTime").getTime())>intelliCourseInfoList.get(j).getDuration()*60*60*1000){
                                        index=k;
                                        intelliCourseInfoList.get(j).setStartTime(avaliableTimeList.get(k).get("startTime"));
                                        Date endTime=new Date(avaliableTimeList.get(k).get("startTime").getTime()+(long)(intelliCourseInfoList.get(j).getDuration()*60*60*1000));
                                        intelliCourseInfoList.get(j).setEndTime(endTime);
                                        break;
                                    }
                                }
                            }
                            if(index!=-1){
                                avaliableTimeList.remove(index);
                            }
                        }
                    }

                    //将排课信息插入数据库
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    if(intelliCourseInfoList!=null&&intelliCourseInfoList.size()>0){
                        for(int j=0;j<intelliCourseInfoList.size();j++){
                            if(intelliCourseInfoList.get(j).getStartTime()!=null&&intelliCourseInfoList.get(j).getEndTime()!=null){
                                CourseArrange courseArrange=new CourseArrange();
                                courseArrange.setTeachingProgramCourseId(teachingProgramCourse.getId());
                                courseArrange.setClassId(teachingProgramCourse.getClassId());
                                courseArrange.setClassName(teachingProgramCourse.getClassName());
                                courseArrange.setTeacherId(teachingProgramCourse.getTeacherId());
                                courseArrange.setTeacherName(teachingProgramCourse.getTeacherName());
                                courseArrange.setClassroomId(teachingProgramCourse.getClassroomId());
                                courseArrange.setClassroomName(teachingProgramCourse.getClassroomName());
                                courseArrange.setStatus(1);
                                courseArrange.setNote("智能排课");
                                courseArrange.setStartTime(intelliCourseInfoList.get(j).getStartTime());
                                courseArrange.setEndTime(intelliCourseInfoList.get(j).getEndTime());
                                courseArrange.setStartTimeForJs(sdf.format(intelliCourseInfoList.get(j).getStartTime()));
                                courseArrange.setEndTimeForJs(sdf.format(intelliCourseInfoList.get(j).getEndTime()));
                                courseArrangeBiz.save(courseArrange);
                            }
                        }
                    }

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/jiaowu/courseArrange/toUpdateCourseArrange.json?classId="+classId;
    }

    /**
     * 根据参数得到上课的时间,因为是以0.5个小时为单位的,如果1.3,会转为1.5.
     * @param leftHour
     * @return
     */
    public Float getDuration(Float leftHour){
        int num=(int)(leftHour/0.5);
        float left=(float)(leftHour%0.5);
        if(left>0){
            leftHour=(float)((num+1)*0.5);
        }else{
            leftHour=(float)(num*0.5);
        }
        return leftHour;
    }


    /**
     * 获取传入的参数之后的18个星期内的所有上课时间
     * @param date
     * @return
     */
    public List<Map<String,Date>> getTermAvalibleTimeList(Date date){
        List<Map<String,Date>> list=new ArrayList<Map<String,Date>>();
        Calendar calendar=Calendar.getInstance();
        Date tempDate=null;
        for(int i=0;i<7*18;i++){
            calendar.setTime(date);
            if((calendar.get(Calendar.DAY_OF_WEEK)!=1)&&(calendar.get(Calendar.DAY_OF_WEEK)!=7)){
                Map<String,Date> map=new HashMap<String,Date>();
                tempDate=new Date(date.getTime());
                tempDate.setHours(8);
                map.put("startTime", tempDate);
                tempDate=new Date(date.getTime());
                tempDate.setHours(12);
                map.put("endTime", tempDate);
                list.add(map);
                Map<String,Date> map2=new HashMap<String,Date>();
                tempDate=new Date(date.getTime());
                tempDate.setHours(14);
                map2.put("startTime", tempDate);
                tempDate=new Date(date.getTime());
                tempDate.setHours(18);
                map2.put("endTime", tempDate);
                list.add(map2);
            }
            date.setDate(date.getDate()+1);
        }
        return list;
    }

    /**
     * 获取一个教学计划课程在某个学期的所有可上课时间
     * @param termAvalibleTimeList
     * @param teachingProgramCourse
     * @return
     */
    public List<Map<String,Date>> getAvaliableTimeOfOneTeachingProgramCourse(List<Map<String,Date>> termAvalibleTimeList,TeachingProgramCourse teachingProgramCourse){
        Long teacherId=teachingProgramCourse.getTeacherId();
        Long classroomId=teachingProgramCourse.getClassroomId();
        Long classId=teachingProgramCourse.getClassId();
        List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," teacherId="+teacherId);
        //根据教师已有的排课信息,取空余时间减去教师上课的时间.获取教师空余的时间.
        subAvaliableTime(courseArrangeList,termAvalibleTimeList);
        //根据教室已有的排课信息,取空余时间减去教室上课的时间.获取教室空余的时间.
        courseArrangeList=courseArrangeBiz.find(null," classroomId="+classroomId);
        subAvaliableTime(courseArrangeList,termAvalibleTimeList);
        //根据班次已有的排课信息,取空余时间减去班次上课的时间.获取班次空余的时间.
        courseArrangeList=courseArrangeBiz.find(null," classId="+classId);
        subAvaliableTime(courseArrangeList,termAvalibleTimeList);
        //以前是取一学期空余的时间与教师上课时间 教师上课时间 班次上课时间的余集
        return termAvalibleTimeList;
    }

    /**
     * 根据已有的排课记录,减少教学计划课程的 有效上课时间.
     * @param courseArrangeList
     * @param termAvalibleTimeList
     */
    public void subAvaliableTime(List<CourseArrange> courseArrangeList,List<Map<String,Date>> termAvalibleTimeList){
        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            for(CourseArrange courseArrange:courseArrangeList){
                //如果排课时间在空闲时间中间,则应增加一个空闲时间Map
                List<Map<String,Date>> addAvaliableTimeList=new ArrayList<Map<String,Date>>();
                //如果排课时间大于空闲时间,则应去掉该空闲时间Map
                List<Integer> subAvaliableTimeList=new ArrayList<Integer>();
                for(int i=0;i<termAvalibleTimeList.size();i++){
//					if((m.get("endTime").getTime()-m.get("startTime").getTime())>=(courseArrange.getEndTime().getTime()-courseArrange.getStartTime().getTime())){
//
//					}
                    Map<String,Date> m=termAvalibleTimeList.get(i);

                    if((courseArrange.getStartTime().getTime()<=m.get("startTime").getTime())&&(courseArrange.getEndTime().getTime()>m.get("startTime").getTime())){
                        m.put("startTime",courseArrange.getEndTime());
                    }
                    if((courseArrange.getStartTime().getTime()<m.get("endTime").getTime())&&(courseArrange.getEndTime().getTime()>m.get("endTime").getTime())){
                        m.put("endTime", courseArrange.getStartTime());
                    }
                    if((courseArrange.getStartTime().getTime()>m.get("startTime").getTime())&&(courseArrange.getEndTime().getTime()<m.get("endTime").getTime())){
                        m.put("endTime", courseArrange.getEndTime());
                        Map<String,Date> addAvaliableTime=new HashMap<String,Date>();
                        addAvaliableTime.put("startTime", courseArrange.getEndTime());
                        addAvaliableTime.put("endTime", m.get("endTime"));
                        addAvaliableTimeList.add(addAvaliableTime);
                    }
                    if((courseArrange.getStartTime().getTime()<=m.get("startTime").getTime())&&(courseArrange.getEndTime().getTime()>=m.get("endTime").getTime())){
                        subAvaliableTimeList.add(i);
                    }
                    if(addAvaliableTimeList.size()>0){
                        for(Map<String,Date> addAvaliableTime:addAvaliableTimeList){
                            termAvalibleTimeList.add(addAvaliableTime);
                        }
                    }
                    if(subAvaliableTimeList.size()>0){
//						termAvalibleTimeList.remove(subAvaliableTimeList.get(0));
                        for(int j=0;j<subAvaliableTimeList.size();j++){
                            termAvalibleTimeList.remove(subAvaliableTimeList.get(j)-j);
                        }
                    }
                }
            }
        }
    }

    @RequestMapping(ADMIN_PREFIX+"/testtermtime")
    @ResponseBody
    public Map<String,Object> getTermAvalibleTimeList(){
        Map<String,Object> json=null;
        Date date=new Date();
        date.setHours(8);
        date.setMinutes(0);
        date.setMinutes(0);
        Date tempDate=null;
        List<Map<String,Date>> list=new ArrayList<Map<String,Date>>();
        Calendar calendar=Calendar.getInstance();
        for(int i=0;i<7*18;i++){
            calendar.setTime(date);
            if((calendar.get(Calendar.DAY_OF_WEEK)!=1)&&(calendar.get(Calendar.DAY_OF_WEEK)!=7)){
                Map<String,Date> map=new HashMap<String,Date>();
                tempDate=new Date(date.getTime());
                tempDate.setHours(8);
                map.put("startTime", tempDate);
                tempDate=new Date(date.getTime());
                tempDate.setHours(12);
                map.put("endTime", tempDate);
                list.add(map);
                Map<String,Date> map2=new HashMap<String,Date>();
                tempDate=new Date(date.getTime());
                tempDate.setHours(14);
                map2.put("startTime", tempDate);
                tempDate=new Date(date.getTime());
                tempDate.setHours(18);
                map2.put("endTime", tempDate);
                list.add(map2);
            }
            date.setDate(date.getDate()+1);
        }
        return json;
    }

	/*@RequestMapping("/addCourseArrange")
	@ResponseBody
	public Map<String,Object> addCourseArrange(HttpServletRequest request){
		Map<String,Object> json=null;
		try{
			CourseArrange courseArrange=new CourseArrange();
			courseArrange.setTeachingProgramCourseId(Long.parseLong(request.getParameter("teachingProgramCourseId")));
			courseArrange.setClassId(Long.parseLong(request.getParameter("classId")));
			String startTime=request.getParameter("startTime");
			courseArrange.setStartTime(new Date(startTime));
			TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(courseArrange.getStartTime());
			calendar.add(Calendar.HOUR_OF_DAY,2);
			courseArrange.setEndTime(calendar.getTime());
			courseArrange.setTeacherId(teachingProgramCourse.getTeacherId());
			courseArrange.setTeacherName(teachingProgramCourse.getTeacherName());
			courseArrange.setStatus(1);
			courseArrangeBiz.save(courseArrange);
		}catch(Exception e){
			logger.info("CourseArrangeController.addCourseArrange",e);
		}
		return json;
	}*/

    /**
     * @Description 某班次排课表上增加一条记录
     * @param request
     * @param courseArrange
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/addCourseArrange")
    @ResponseBody
    public Map<String,Object> addCourseArrange(HttpServletRequest request,@ModelAttribute("courseArrange") CourseArrange courseArrange,@RequestParam("beginTime")String beginTime){
        Map<String,Object> json=null;
        try{

            Date startTime = new Date(beginTime);
            courseArrange.setStartTimeForJs(beginTime);
            courseArrange.setStartTime(startTime);
            TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            //判断剩余学时
            if(teachingProgramCourse.getTotalHour()-teachingProgramCourse.getLearnedHour()<2){
                json=this.resultJson(ErrorCode.ERROR_DATA, "剩余学时不足2个小时,所以不能添加.", null);
                return json;
            }
			/*Classes classes=classesBiz.findById(teachingProgramCourse.getClassId());
			Classroom classroom=classroomBiz.findById(teachingProgramCourse.getClassroomId());
			if(classroom.getNum()<classes.getStudentTotalNum()){
				json=this.resultJson(ErrorCode.ERROR_DATA, "剩余学时不足2个小时,所以不能添加.", null);
				return json;
			}*/

            Calendar calendar=Calendar.getInstance();
            calendar.setTime(courseArrange.getStartTime());
            //默认课程上课时间为2小时
            calendar.add(Calendar.HOUR_OF_DAY,2);
            courseArrange.setEndTime(calendar.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));


            courseArrange.setTeacherId(teachingProgramCourse.getTeacherId());
            if(!checkTeacherConflict(courseArrange)){
                //判断该班次上课时间是否发生冲突
                if(checkClassConflict(courseArrange)){
                    json=this.resultJson(ErrorCode.ERROR_DATA, "班次上课时间冲突", null);
                    return json;
                }
                //判断教室的上课时间是否发生冲突
                if(checkClassroomConflict(courseArrange)){
                    json=this.resultJson(ErrorCode.ERROR_DATA, "教室上课时间冲突", null);
                    return json;
                }
                courseArrange.setClassName(teachingProgramCourse.getClassName());
                courseArrange.setTeacherName(teachingProgramCourse.getTeacherName());
                //排课的教室由教学计划课程决定
                courseArrange.setClassroomId(teachingProgramCourse.getClassroomId());
                courseArrange.setClassroomName(teachingProgramCourse.getClassroomName());
                courseArrange.setStatus(1);

                courseArrangeBiz.save(courseArrange);
                //修改教学计划课程已学的学时
                teachingProgramCourse.setLearnedHour(teachingProgramCourse.getLearnedHour()+2);

                //添加教学动态记录
                TeachingInfo teachingInfo=new TeachingInfo();
                teachingInfo.setTitle(teachingProgramCourse.getClassName()+"班次新增了一条排课记录");
                teachingInfo.setContent(courseArrange.getClassName()+"在"+dateFormat(new Date())+"新增了一条排课记录,课程为"+teachingProgramCourse.getCourseName()+",课程开始时间为"+dateFormat(courseArrange.getStartTime())+",课程结束时间为"+dateFormat(courseArrange.getEndTime())+",教室在"+courseArrange.getClassroomName()+",授课教师是"+courseArrange.getTeacherName()+"。");
                teachingInfo.setType(1);
                teachingInfo.setClickTimes(0L);
                teachingInfo.setStatus(1);
                teachingInfoBiz.save(teachingInfo);

                json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrange.getId());

            }else{
                json=this.resultJson(ErrorCode.ERROR_DATA, "讲师冲突", null);
            }
        }catch(Exception e){
            logger.info("CourseArrangeController.addCourseArrange",e);
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    public String dateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * @Description 检测教师的授课时间是否发生冲突
     * @param courseArrange
     * @return boolean
     */
    public boolean checkTeacherConflict(CourseArrange courseArrange){
        //由于可以支持共享课程,所以一位教师可以在一间教室对多个班次进行上课.所以可能存在同一时间段内多个上课的记录.

		/*String whereSql=" startTime < '"+courseArrange.getStartTime()+"' and endTime > '"+courseArrange.getStartTime()+" '";
		List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null,whereSql);
		if(courseArrangeList!=null&&courseArrangeList.size()>0)*/
        if(courseArrange.getTeacherId()==null||courseArrange.getTeacherId()==0){
            TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            courseArrange.setTeacherId(teachingProgramCourse.getTeacherId());
        }

        String whereSql=" teacherId="+courseArrange.getTeacherId();
        if(courseArrange.getId()!=null&&courseArrange.getId()!=0){
            whereSql+=" and id!="+courseArrange.getId();
        }
        //判断该教师上课的所有记录
        List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null,whereSql);
        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            for(CourseArrange courseArrange2:courseArrangeList){
                //如果上课时间发生冲突
                if((courseArrange2.getStartTime().getTime()<=courseArrange.getStartTime().getTime())&&(courseArrange2.getEndTime().getTime()>courseArrange.getStartTime().getTime())){
                    return true;
                }
                if((courseArrange2.getStartTime().getTime()<courseArrange.getEndTime().getTime())&&(courseArrange2.getEndTime().getTime()>=courseArrange.getEndTime().getTime())){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

    /**
     * @Description 检测该班次的上课时间是否发生冲突
     * @param courseArrange
     * @return boolean
     */
    public boolean checkClassConflict(CourseArrange courseArrange){
        if(courseArrange.getClassId()==null||courseArrange.getClassId()==0L){
            TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            courseArrange.setClassId(teachingProgramCourse.getClassId());
        }
        String whereSql=" classId="+courseArrange.getClassId();
        if(courseArrange.getId()!=null&&courseArrange.getId()!=0){
            whereSql+=" and id!="+courseArrange.getId();
        }
        //判断该班次上课的所有记录
        List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null,whereSql);
        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            for(CourseArrange courseArrange2:courseArrangeList){
                //如果上课时间发生冲突
                if((courseArrange2.getStartTime().getTime()<=courseArrange.getStartTime().getTime())&&(courseArrange2.getEndTime().getTime()>courseArrange.getStartTime().getTime())){
                    return true;
                }
                if((courseArrange2.getStartTime().getTime()<courseArrange.getEndTime().getTime())&&(courseArrange2.getEndTime().getTime()>=courseArrange.getEndTime().getTime())){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

    /**
     * @Description 检测该教室的上课时间是否发生冲突
     * @param courseArrange
     * @return boolean
     */
    public boolean checkClassroomConflict(CourseArrange courseArrange){
        if(courseArrange.getClassroomId()==null||courseArrange.getClassroomId()==0L){
            TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            courseArrange.setClassroomId(teachingProgramCourse.getClassroomId());
        }
        String whereSql=" classroomId="+courseArrange.getClassroomId();
        if(courseArrange.getId()!=null&&courseArrange.getId()!=0){
            whereSql+=" and id!="+courseArrange.getId();
        }
        //判断该班次上课的所有记录
        List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null,whereSql);
        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            for(CourseArrange courseArrange2:courseArrangeList){
                //如果上课时间发生冲突
                if((courseArrange2.getStartTime().getTime()<=courseArrange.getStartTime().getTime())&&(courseArrange2.getEndTime().getTime()>courseArrange.getStartTime().getTime())){
                    return true;
                }
                if((courseArrange2.getStartTime().getTime()<courseArrange.getEndTime().getTime())&&(courseArrange2.getEndTime().getTime()>=courseArrange.getEndTime().getTime())){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

	/*@RequestMapping("/getCourseArrangeByClassId")
	public String getCourseArrangeByClassId(HttpServletRequest request,@RequestParam("classId")Long classId){
		try{
			String whereSQL="";
			Calendar calendar=Calendar.getInstance();
			Date now=new Date();
			calendar.setTime(now);
			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			Date startTime=calendar.getTime();
			calendar.add(Calendar.DATE, 7);
			Date endTime=calendar.getTime();
			whereSQL+=" startTime>"+startTime+" and endTime<"+endTime+" and classId="+classId;
			List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null,whereSQL);
			request.setAttribute("courseArrangeList",courseArrangeList);
		}catch(Exception e){
			logger.info("CourseArrangeController.getCourseArrangeByClassId",e);
		}
		return oneClassCourseArrange;
	}*/


    /**
     * @Description 删除某排课记录
     * @param request
     * @param id
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/delCourseArrange")
    @ResponseBody
    public Map<String,Object> delCourseArrange(HttpServletRequest request,@RequestParam("id")Long id){
        Map<String,Object> json=null;
        try{
            CourseArrange courseArrange=courseArrangeBiz.findById(id);
            TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            courseArrangeBiz.deleteById(id);
            //添加教学动态
            TeachingInfo teachingInfo=new TeachingInfo();
            teachingInfo.setTitle(courseArrange.getClassName()+"班次删除了一条排课记录");
            teachingInfo.setContent(courseArrange.getClassName()+"在"+dateFormat(new Date())+"删除了一条排课记录,课程为"+teachingProgramCourse.getCourseName()+",课程开始时间为"+dateFormat(courseArrange.getStartTime())+",课程结束时间为"+dateFormat(courseArrange.getEndTime())+",教室在"+courseArrange.getClassroomName()+",授课教师是"+courseArrange.getTeacherName()+"。");
            teachingInfo.setType(1);
            teachingInfo.setClickTimes(0L);
            teachingInfo.setStatus(1);
            teachingInfoBiz.save(teachingInfo);
            //修改教学计划课程已学的学时
            float hour=getHour(courseArrange);
            teachingProgramCourse.setLearnedHour(teachingProgramCourse.getLearnedHour()-hour);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 更新某排课记录的开始和结束时间
     * @param request
     * @param courseArrange
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/updateCourseArrangeBeginAndEndTime")
    @ResponseBody
    public Map<String,Object> updateCourseArrangeBeginAndEndTime(HttpServletRequest request,@ModelAttribute("courseArrange")CourseArrange courseArrange){
        Map<String,Object> json=null;
        try{
            TeachingProgramCourse teachingProgramCourseForHour=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            CourseArrange originCourseArrange=null;
            if(courseArrange.getId()!=null&&courseArrange.getId()!=0){
                originCourseArrange=courseArrangeBiz.findById(courseArrange.getId());
            }

            courseArrange.setStartTime(new Date(courseArrange.getStartTimeForJs()));
            if((courseArrange.getEndTimeForJs()==null)||(courseArrange.getEndTimeForJs().equals(""))||(courseArrange.getEndTimeForJs().equals("null"))){
                Date startTime=new Date(courseArrange.getStartTimeForJs());
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(startTime);
                calendar.add(Calendar.HOUR_OF_DAY,2);
                Date endTime=calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                courseArrange.setEndTimeForJs(sdf.format(endTime));

                calendar.setTime(courseArrange.getStartTime());
                calendar.add(Calendar.HOUR_OF_DAY,2);
                courseArrange.setEndTime(calendar.getTime());
            }else{
                courseArrange.setEndTime(new Date(courseArrange.getEndTimeForJs()));
            }

            Long courseArrangeId=courseArrange.getId();
            String errorMessage=null;
            boolean hasConflict=false;
            //判断教室上课时间是否发生冲突
            boolean teacherConflict=checkTeacherConflict(courseArrange);
            if(teacherConflict){
                errorMessage="讲师授课时间冲突";
            }
            //判断班次上课时间是否发生冲突
            boolean classConflict=checkClassConflict(courseArrange);
            if(classConflict){
                errorMessage="班次上课时间冲突";
            }
            //判断教室上课时间是否发生冲突
            boolean classroomConflict=checkClassroomConflict(courseArrange);
            if(classroomConflict){
                errorMessage="教室上课时间冲突";
            }
            //如果三者任意一个为true,则发生了冲突.
            if(teacherConflict||classConflict||classroomConflict){
                hasConflict=true;
            }
            //如果该排课记录已经存在
            if(courseArrangeId!=null&&courseArrangeId!=0){
                //如果发生了冲突
                if(hasConflict){
                    //之前没有发生冲突,已经存到数据库中,现在发生了冲突,则删除
                    courseArrangeBiz.deleteById(courseArrange.getId());
                    //减去教学计划课程已学的学时
                    float originHour=getHour(originCourseArrange);
                    teachingProgramCourseForHour.setLearnedHour(teachingProgramCourseForHour.getLearnedHour()-originHour);
                    teachingProgramCourseBiz.update(teachingProgramCourseForHour);
                    //添加教学动态
                    TeachingInfo teachingInfo=new TeachingInfo();
                    teachingInfo.setTitle(originCourseArrange.getClassName()+"班次删除了一条排课记录");
                    teachingInfo.setContent(originCourseArrange.getClassName()+"在"+dateFormat(new Date())+"因为教师的授课时间发生了冲突,删除了一条排课记录,课程为"+teachingProgramCourseForHour.getCourseName()+",课程开始时间为"+dateFormat(originCourseArrange.getStartTime())+",课程结束时间为"+dateFormat(originCourseArrange.getEndTime())+",教室在"+originCourseArrange.getClassroomName()+",授课教师是"+originCourseArrange.getTeacherName()+"。");
                    teachingInfo.setType(1);
                    teachingInfo.setClickTimes(0L);
                    teachingInfo.setStatus(1);
                    teachingInfoBiz.save(teachingInfo);
                    json=this.resultJson("false", errorMessage, null);
                }else{
					/*courseArrange.setStartTime(new Date(courseArrange.getStartTimeForJs()));
					System.out.println(courseArrange.getEndTimeForJs());
					System.out.println(courseArrange.getEndTimeForJs().equals("null"));
					if((courseArrange.getEndTimeForJs()==null)||(courseArrange.getEndTimeForJs().equals(""))||(courseArrange.getEndTimeForJs().equals("null"))){
						Date startTime=new Date(courseArrange.getStartTimeForJs());
						Calendar calendar=Calendar.getInstance();
						calendar.setTime(startTime);
						calendar.add(Calendar.HOUR_OF_DAY,2);
						Date endTime=calendar.getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
						courseArrange.setEndTimeForJs(sdf.format(endTime));

						calendar.setTime(courseArrange.getStartTime());
						calendar.add(Calendar.HOUR_OF_DAY,2);
						courseArrange.setEndTime(calendar.getTime());
					}else{
						courseArrange.setEndTime(new Date(courseArrange.getEndTimeForJs()));
					}*/
                    //没有发生冲突
                    courseArrangeBiz.update(courseArrange);

                    float originHour=getHour(originCourseArrange);
                    float currentHour=getHour(courseArrange);
                    teachingProgramCourseForHour.setLearnedHour(teachingProgramCourseForHour.getLearnedHour()+currentHour-originHour);
                    teachingProgramCourseBiz.update(teachingProgramCourseForHour);
                    //添加教学动态
                    TeachingInfo teachingInfo=new TeachingInfo();
                    teachingInfo.setTitle(originCourseArrange.getClassName()+"班次更新了一条排课记录");
                    teachingInfo.setContent(originCourseArrange.getClassName()+"在"+dateFormat(new Date())+"更新了一条排课记录,原来课程开始时间为"+dateFormat(originCourseArrange.getStartTime())+",课程结束时间为:"+dateFormat(originCourseArrange.getEndTime())+"现改为课程开始时间为"+dateFormat(courseArrange.getStartTime())+",课程结束时间为:"+dateFormat(courseArrange.getEndTime())+",课程为"+teachingProgramCourseForHour.getCourseName()+",教室在"+originCourseArrange.getClassroomName()+",授课教师是"+originCourseArrange.getTeacherName()+"。");
                    teachingInfo.setType(1);
                    teachingInfo.setClickTimes(0L);
                    teachingInfo.setStatus(1);
                    teachingInfoBiz.save(teachingInfo);
                    json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrange.getId());
                }
            }else{
                //如果之前没有保存到数据库中,且冲突.
                if(hasConflict){
                    json=this.resultJson("false", errorMessage, null);
                }else{
					/*courseArrange.setStartTime(new Date(courseArrange.getStartTimeForJs()));
					System.out.println(courseArrange.getEndTimeForJs());
					System.out.println(courseArrange.getEndTimeForJs().equals("null"));
					if((courseArrange.getEndTimeForJs()==null)||(courseArrange.getEndTimeForJs().equals(""))||(courseArrange.getEndTimeForJs().equals("null"))){
						Date startTime=new Date(courseArrange.getStartTimeForJs());
						Calendar calendar=Calendar.getInstance();
						calendar.setTime(startTime);
						calendar.add(Calendar.HOUR_OF_DAY,2);
						Date endTime=calendar.getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
						courseArrange.setEndTimeForJs(sdf.format(endTime));

						calendar.setTime(courseArrange.getStartTime());
						calendar.add(Calendar.HOUR_OF_DAY,2);
						courseArrange.setEndTime(calendar.getTime());
					}else{
						courseArrange.setEndTime(new Date(courseArrange.getEndTimeForJs()));
					}*/
                    //原来没有改记录,且不冲突,则增加该排课记录.
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setTeacherId(teachingProgramCourse.getTeacherId());
                    courseArrange.setClassName(teachingProgramCourse.getClassName());
                    courseArrange.setTeacherName(teachingProgramCourse.getTeacherName());
                    //排课的教室由教学计划课程决定
                    courseArrange.setClassroomId(teachingProgramCourse.getClassroomId());
                    courseArrange.setClassroomName(teachingProgramCourse.getClassroomName());
                    courseArrange.setStatus(1);
                    courseArrangeBiz.save(courseArrange);

                    //增加已学学时
                    float hour=getHour(courseArrange);
                    teachingProgramCourseForHour.setLearnedHour(teachingProgramCourseForHour.getLearnedHour()+hour);
                    teachingProgramCourseBiz.update(teachingProgramCourseForHour);
                    //添加教学动态
                    TeachingInfo teachingInfo=new TeachingInfo();
                    teachingInfo.setTitle(courseArrange.getClassName()+"班次新增了一条排课记录");
                    teachingInfo.setContent(courseArrange.getClassName()+"在"+dateFormat(new Date())+"新增了一条排课记录,课程为"+teachingProgramCourse.getCourseName()+",课程开始时间为"+dateFormat(courseArrange.getStartTime())+",课程结束时间为"+dateFormat(courseArrange.getEndTime())+",教室在"+courseArrange.getClassroomName()+",授课教师是"+courseArrange.getTeacherName()+"。");
                    teachingInfo.setType(1);
                    teachingInfo.setClickTimes(0L);
                    teachingInfo.setStatus(1);
                    teachingInfoBiz.save(teachingInfo);
                    json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrange.getId());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;

    }

    /**
     * @Description 获取某班次课表
     * @param request
     * @param classId
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/getCourseArrangeByClassId")
    @ResponseBody
    public Map<String,Object> getCourseArrangeByClassId(HttpServletRequest request,@RequestParam("classId")Long classId){
        Map<String,Object> json=null;
        try{
            Date now=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," classId="+classId + " and status = 1");
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for(CourseArrange courseArrange:courseArrangeList){
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse.getCourseName());
//					courseArrange.setStartTimeForJs(sdf.format(courseArrange.getStartTime()));
//					courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));
                    //如果排课开始时间小于当前时间,则不可编辑.
                    if(courseArrange.getStartTime().getTime()<now.getTime()){
                        courseArrange.setEditable(false);
                    }else{
                        courseArrange.setEditable(true);
                    }
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 班次课表
     * @param request
     * @param classId
     * @return String
     */
    @RequestMapping(ADMIN_PREFIX+"/classCourseArrange")
    public String classCourseArrange(HttpServletRequest request,@RequestParam("classId")Long classId){
        try{
            request.setAttribute("classId", classId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return classCourseArrange;
    }

    /**
     * @Description  ajax获取一个班级的课表内容
     * @param request
     * @param classId
     * @return Map
     */
    @RequestMapping(ADMIN_PREFIX+"/ajaxClassCourseArrange")
    @ResponseBody
    public Map<String,Object> ajaxClassCourseArrange(HttpServletRequest request,@RequestParam("classId")Long classId){
        Map<String,Object> json=null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," classId="+classId+" status = 1");
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for(CourseArrange courseArrange:courseArrangeList){
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse.getCourseName());
//					courseArrange.setStartTimeForJs(sdf.format(courseArrange.getStartTime()));
//					courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 教师课表
     * @param request
     * @param teacherId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/teacherCourseArrange")
    public String teacherCourseArrange(HttpServletRequest request,@RequestParam(value="teacherId",required = false)Long teacherId){
        try{
            if(teacherId==null||teacherId.equals(0L)){
                Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
                if(!userMap.get("userType").equals("2")){
                    return null;
                }
                teacherId=Long.parseLong(userMap.get("linkId"));
            }
            request.setAttribute("teacherId", teacherId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return teacherCourseArrange;
    }

    /**
     * @Description ajax获取教师课表的内容
     * @param request
     * @param teacherId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/ajaxTeacherCourseArrange")
    @ResponseBody
    public Map<String,Object> ajaxTeacherCourseArrange(HttpServletRequest request,@RequestParam("teacherId")Long teacherId){
        Map<String,Object> json=null;
        try{

            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," teacherId="+teacherId);
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for(CourseArrange courseArrange:courseArrangeList){
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse.getCourseName());
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 教室课表
     * @param request
     * @param classroomId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/classroomCourseArrange")
    public String classroomCourseArrange(HttpServletRequest request,@RequestParam("classroomId")Long classroomId){
        try{
            request.setAttribute("classroomId", classroomId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return classroomCourseArrange;
    }

    /**
     * @Description ajax获取教室课表的内容
     * @param request
     * @param classroomId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/ajaxClassroomCourseArrange")
    @ResponseBody
    public Map<String,Object> ajaxClassroomCourseArrange(HttpServletRequest request,@RequestParam("classroomId")Long classroomId){
        Map<String,Object> json=null;
        try{

            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," classroomId="+classroomId);
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for(CourseArrange courseArrange:courseArrangeList){
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse.getCourseName());
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 学员课表
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/studentCourseArrange")
    public String studentCourseArrange(HttpServletRequest request,@RequestParam(value="userId",required = false)Long userId){
        try{
            if(userId==null||userId.equals(0L)){
                Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
                userId=Long.parseLong(userMap.get("linkId"));
            }
            request.setAttribute("userId", userId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return studentCourseArrange;
    }

    /**
     * @Description ajax获取学员课表的内容
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/ajaxStudentCourseArrange")
    @ResponseBody
    public Map<String,Object> ajaxStudentCourseArrange(HttpServletRequest request,@RequestParam("userId")Long userId){
        Map<String,Object> json=null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            User user=userBiz.findById(userId);
            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," classId="+user.getClassId()+" and status = 1");
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for (CourseArrange courseArrange : courseArrangeList) {
                    Course course = courseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(course.getId());
                    courseArrange.setCourseName(course.getName());
                    courseArrange.setStartTimeForJs(sdf.format(courseArrange.getStartTime()));
                    courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 全校总课表
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/schoolCourseArrange")
    public String schoolCourseArrange(HttpServletRequest request){
        try{

        }catch(Exception e){
            e.printStackTrace();
        }
        return schoolCourseArrange;
    }

    /**
     * @Description ajax获取全校总课表的内容
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/ajaxSchoolCourseArrange")
    @ResponseBody
    public Map<String,Object> ajaxSchoolCourseArrange(HttpServletRequest request){
        Map<String,Object> json=null;
        try{

            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," 1=1");
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for(CourseArrange courseArrange:courseArrangeList){
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse.getCourseName());
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
    /**
     * @Description 教师课表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     *
     */
    @RequestMapping(ADMIN_PREFIX+"/teacherActivity")
    public String teacherActivity(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
        try{
            SimpleDateFormat  cstFormater = new SimpleDateFormat(" EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Long teacherId=Long.parseLong(request.getParameter("teacherId"));
            String whereSql=" teacherId="+teacherId;
            CourseArrange courseArrange=new CourseArrange();
            courseArrange.setTeacherId(teacherId);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime=request.getParameter("startTime");
            if(!StringUtils.isTrimEmpty(startTime)){
                if(startTime.contains("CST")){
                    startTime=sdf.format(cstFormater.parse(startTime));
                }
                whereSql+=" and startTime > '"+startTime+"'";
                courseArrange.setStartTime(sdf.parse(startTime));
            }
            String endTime=request.getParameter("endTime");
            if(!StringUtils.isTrimEmpty(endTime)){
                if(endTime.contains("CST")){
                    endTime=sdf.format(cstFormater.parse(endTime));
                }
                whereSql+=" and endTime < '"+endTime+"'";
                courseArrange.setEndTime(sdf.parse(endTime));
            }
            pagination.setRequest(request);
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(pagination,whereSql);
            request.setAttribute("courseArrangeList",courseArrangeList);
            request.setAttribute("pagination",pagination);
            request.setAttribute("courseArrange", courseArrange);
        }catch(Exception e){
            e.printStackTrace();
        }
        return teacherActivity;
    }

    /**
     * @Description 查询某排课记录信息
     * @param id
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/queryCourseArrange")
    @ResponseBody
    public Map<String,Object> queryCourseArrange(@RequestParam("id")Long id){
        Map<String,Object> json=null;
        try{
            CourseArrange courseArrange=courseArrangeBiz.findById(id);
            TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            courseArrange.setCourseId(teachingProgramCourse.getCourseId());
            courseArrange.setCourseName(teachingProgramCourse.getCourseName());
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrange);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * @Description 查看一条排课记录的详情,并且可以修改课程的上课教室.
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     *
     */
    @RequestMapping(ADMIN_PREFIX+"/toUpdateOneCourseArrange")
    public String toUpdateOneCourseArrange(HttpServletRequest request,@RequestParam("id")Long id){
        /*try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CourseArrange courseArrange=courseArrangeBiz.findById(id);
            TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            courseArrange.setCourseId(teachingProgramCourse.getCourseId());
            courseArrange.setCourseName(teachingProgramCourse.getCourseName());
            courseArrange.setStartTimeForJs(sdf.format(courseArrange.getStartTime()));
            courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));
            request.setAttribute("courseArrange", courseArrange);

            //下面是为了获取该排课记录是否与其它班次分享课程
            Classes classes=classesBiz.findById(courseArrange.getClassId());
            List<Classes> otherClassList=new ArrayList<Classes>();
            //获取同一班型下的班次列表
            List<Classes> classList=classesBiz.find(null," classTypeId="+classes.getClassTypeId()+" and id!="+classes.getId());
            if(classList!=null&&classList.size()>0){
                List<Classes> classList2=new ArrayList<Classes>();
                for(Classes tempClass:classList){
                    //如果班次的教学计划课程也有该排课对应的课程
                    List<TeachingProgramCourse> teachingProgramCourseList=teachingProgramCourseBiz.find(null," classId="+tempClass.getId()+" and courseId="+courseArrange.getCourseId());
                    if(teachingProgramCourseList!=null&&teachingProgramCourseList.size()>0){
                        classList2.add(tempClass);
                    }
                }
                //如果班次在该排课时间段内有课程,则冲突,去除该班次.
                if(classList2!=null&&classList2.size()>0){
                    for(Classes tempClass:classList2){
                        String whereSql=" classId="+tempClass.getId()+" and ((startTime<='"+courseArrange.getStartTime()+"' and endTime>'"+courseArrange.getStartTime()+"') or (startTime<'"+courseArrange.getEndTime()+"' and endTime>='"+courseArrange.getEndTime()+"'))";
                        List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null,whereSql);
                        if(courseArrangeList==null||courseArrangeList.size()==0){
                            otherClassList.add(tempClass);
                        }
                    }
                }
            }


            if(otherClassList!=null&&otherClassList.size()>0){
                for(Classes tempClasses:otherClassList){
                    //如果能获取到记录,说明该班次有排课记录是现排课记录的分享课程
                    List<CourseArrange> tempCourseArrangeList=courseArrangeBiz.find(null," classId="+tempClasses.getId()+" and shareCourseArrangeId="+courseArrange.getId());
                    if(tempCourseArrangeList!=null&&tempCourseArrangeList.size()>0){
                        tempClasses.setHasShareCourse(1);
                    }else{
                        tempClasses.setHasShareCourse(0);
                    }
                }
            }
            request.setAttribute("classList", otherClassList);
        }catch(Exception e){
            e.printStackTrace();
        }*/
        try{
            //如果当前学员不属于管理员，则不允许修改
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("isAdmin", userMap.get("userType").equals("1") ? true : false);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CourseArrange courseArrange = courseArrangeBiz.findById(id);
            courseArrange.setCourseId(courseArrange.getTeachingProgramCourseId());
            Course course = courseBiz.findById(courseArrange.getTeachingProgramCourseId());
            if (ObjectUtils.isNotNull(course)) {
                courseArrange.setCourseName(course.getName());
            }
            courseArrange.setStartTimeForJs(sdf.format(courseArrange.getStartTime()));
            courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));
            request.setAttribute("courseArrange", courseArrange);
        }catch (Exception e){
            e.printStackTrace();
        }
        return updateOneCourseArrange;
    }

    /**
     * @Description 修改一条排课记录的上课教室和教师
     * @param request
     * @param courseArrange
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/updateOneCourseArrange")
    @ResponseBody
    public Map<String,Object> updateOneCourseArrange(HttpServletRequest request,@ModelAttribute("courseArrange")CourseArrange courseArrange){
        Map<String,Object> json=null;
        try{


            CourseArrange newCourseArrange=courseArrangeBiz.findById(courseArrange.getId());
            newCourseArrange.setClassroomId(courseArrange.getClassroomId());
            newCourseArrange.setClassroomName(courseArrange.getClassroomName());
            newCourseArrange.setTeacherId(courseArrange.getTeacherId());
            newCourseArrange.setTeacherName(courseArrange.getTeacherName());
            //判断同一时间教师是否发生冲突
            String whereSql=" teacherId="+newCourseArrange.getTeacherId()+" and ((startTime<='"+courseArrange.getStartTime()+"' and endTime>'"+courseArrange.getStartTime()+"') or (startTime<'"+courseArrange.getEndTime()+"' and endTime>='"+courseArrange.getEndTime()+"'))";
            List<CourseArrange> courseArrangeListForTeacher=courseArrangeBiz.find(null,whereSql);
            if(courseArrangeListForTeacher!=null&&courseArrangeListForTeacher.size()>0){
                json=this.resultJson(ErrorCode.ERROR_DATA, "该教师在该时间段内已有课程,请选择其他教师!", null);
                return json;
            }
            //判断座位数是否足够,注意还有分享课程的班次.
            Classroom classroom=classroomBiz.findById(newCourseArrange.getClassroomId());
            String otherClassIds=request.getParameter("otherClassIds");
            Classes currentClasses=classesBiz.findById(newCourseArrange.getClassId());
            Long studentNum=currentClasses.getStudentTotalNum();
            if(!StringUtils.isTrimEmpty(otherClassIds)){
                List<Classes> fenxiangclassList=classesBiz.find(null," id in ("+otherClassIds.substring(0,otherClassIds.length()-1)+")");
                if(fenxiangclassList!=null&&fenxiangclassList.size()>0){
                    for(Classes fenxiangClass:fenxiangclassList){
                        studentNum+=fenxiangClass.getStudentTotalNum();
                    }
                }
            }
            if(studentNum>classroom.getNum()){
                json=this.resultJson(ErrorCode.ERROR_DATA, "该教室座位数小于学员总人数,请重新选择!", null);
                return json;
            }

            courseArrangeBiz.update(newCourseArrange);
            Long newCourseArrangeId=newCourseArrange.getId();
            CourseArrange copyCourseArrange=newCourseArrange;
            copyCourseArrange.setId(null);

            //删除原来分来分享课程的排课记录
            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," shareCourseArrangeId="+newCourseArrangeId);
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                List<Long> ids=new ArrayList<Long>();
                for(CourseArrange tempCourseArrange:courseArrangeList){
                    ids.add(tempCourseArrange.getId());
                }
                courseArrangeBiz.deleteByIds(ids);
            }
            //重新添加分享课程的排课记录

            if(!StringUtils.isTrimEmpty(otherClassIds)){
                String[] classIdArray=otherClassIds.split(",");
                if(classIdArray!=null&&classIdArray.length>0){
                    for(String classId:classIdArray){
                        Classes classes=classesBiz.findById(Long.parseLong(classId));
                        copyCourseArrange.setClassId(classes.getId());
                        copyCourseArrange.setClassName(classes.getName());
                        copyCourseArrange.setShareCourse(1);
                        copyCourseArrange.setShareCourseArrangeId(newCourseArrangeId);
                        courseArrangeBiz.save(copyCourseArrange);
                    }
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description ajax分页获取学员课表的内容
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/appStudentCourseArrange")
    @ResponseBody
    public Map<String,Object> appStudentCourseArrange(HttpServletRequest request,@RequestParam("userId")Long userId,@ModelAttribute("pagination") Pagination pagination){
        Map<String,Object> json=null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            User user=userBiz.findById(userId);
            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(pagination," classId="+user.getClassId()+" and status = 1");
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for (CourseArrange courseArrange : courseArrangeList) {
                    Course course = courseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(course.getId());
                    courseArrange.setCourseName(course.getName());
                    courseArrange.setStartTimeForJs(sdf.format(courseArrange.getStartTime()));
                    courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));
                }
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    public Map<String,Date> getSuitableTime(Long teachingProgramCourseId,Date startTime,float duration,List<CourseArrange> courseArrangeList){
        TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(teachingProgramCourseId);
        Map<String,Date> map=new HashMap<String,Date>();
        int addHour=(int)(duration/1.5);
        int addMinute=duration%1.5==0?0:30;
        boolean findCorrectTime=false;
        startTime.setHours(8);
        startTime.setMinutes(0);
        startTime.setSeconds(0);
        Date endTime=startTime;
        endTime.setHours(endTime.getHours()+addHour);
        endTime.setMinutes(endTime.getMinutes()+addMinute);
        while(!findCorrectTime){
            for(CourseArrange courseArrange:courseArrangeList){

            }
        }
        return map;
    }

    /**
     * 获取可用时间集合,date为新学期第一天,minute和seconds要设置为0.courseArrangeList为
     * @param date
     * @param courseArrangeList
     * @return
     */
    public List<Map<String,Date>> getAvaliableTimeList(Date date,List<CourseArrange> courseArrangeList){
        List<Map<String,Date>> list=new ArrayList<Map<String,Date>>();
        Calendar calendar=Calendar.getInstance();
        for(int i=0;i<5*18;i++){
            calendar.setTime(date);
            if((calendar.get(Calendar.DAY_OF_WEEK)==1)||(calendar.get(Calendar.DAY_OF_WEEK)==7)){
                Map<String,Date> map=new HashMap<String,Date>();
                date.setHours(8);
                map.put("startTime", date);
                date.setHours(12);
                map.put("endTime", date);
                list.add(map);
                Map<String,Date> map2=new HashMap<String,Date>();
                date.setHours(14);
                map2.put("startTime", date);
                date.setHours(18);
                map2.put("endTime", date);
                list.add(map);
            }
            date.setDate(date.getDate()+1);
        }

        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            for(CourseArrange courseArrange:courseArrangeList){
                for(Map<String,Date> m:list){
                    if((m.get("endTime").getTime()-m.get("startTime").getTime())>=(courseArrange.getEndTime().getTime()-courseArrange.getStartTime().getTime())){

                    }
                }
            }
        }
        return list;
    }

    /**
     * @Description 获取某班次课表
     * @param request
     * @param classId
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/courseArrangeListOfOneClass")
    public String courseArrangeListOfOneClass(HttpServletRequest request,@RequestParam("classId")Long classId){
        try{
            if(classId==null||classId.equals(0L)){
                Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);
                if(userMap.get("userType").equals("2")){

                }else{
                    classId=0L;
                }
            }
            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," classId="+classId);
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for(CourseArrange courseArrange:courseArrangeList){
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse.getCourseName());

                }
            }
            request.setAttribute("courseArrangeList",courseArrangeList);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/admin/courseArrange/courseArrangeListOfOneClass";
    }

    /**
     * 导出某班次排课表
     * @param request
     * @param response
     * @param classId
     */
    @RequestMapping(ADMIN_PREFIX+"/courseArrangeExcel")
    public void courseArrangeExcel(HttpServletRequest request, HttpServletResponse response,Long classId){
        try{
            File file=courseArrangeBiz.courseArrangeExcel(request,classId);
            List<File> fileList=new ArrayList<File>();
            fileList.add(file);
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/courseArrange");
            String expName = "课程表_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            FileExportImportUtil.createRar(response, dir, fileList, expName);// 生成的多excel的压缩包
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * @Description 获取当前登录学员已上课记录
     * @param request
     * @return java.util.Map
     */
    @RequestMapping(ADMIN_PREFIX+"/courseArrangeListOfCurrentStudent")
    public String courseArrangeListOfCurrentStudent(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
        try{
            Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);
            if(!userMap.get("userType").equals("3")){
                return null;
            }
            User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
            pagination.setRequest(request);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<CourseArrange> courseArrangeList=courseArrangeBiz.find(pagination," classId="+user.getClassId()+" and status = 1");
            if(courseArrangeList!=null&&courseArrangeList.size()>0){
                for (CourseArrange courseArrange : courseArrangeList) {
                    Course course = courseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    courseArrange.setCourseId(course.getId());
                    courseArrange.setCourseName(course.getName());
                    courseArrange.setStartTimeForJs(sdf.format(courseArrange.getStartTime()));
                    courseArrange.setEndTimeForJs(sdf.format(courseArrange.getEndTime()));
                    if(new Date().after(courseArrange.getEndTime())){
                        courseArrange.setFlag(true);
                    }
                }
            }
            request.setAttribute("courseArrangeList",courseArrangeList);
            request.setAttribute("pagination",pagination);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/admin/courseArrange/courseArrangeListOfCurrentStudent";
    }
}
