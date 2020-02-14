package com.jiaowu.biz.courseArrangeEvaluate;

import com.a_268.base.core.BaseBiz;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.dao.courseArrangeEvaluate.CourseArrangeEvaluateDao;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.courseArrangeEvaluate.CourseArrangeEvaluate;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 李帅雷 on 2017/10/17.
 */
@Service
public class CourseArrangeEvaluateBiz extends BaseBiz<CourseArrangeEvaluate,CourseArrangeEvaluateDao> {
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;

    /**
     *每一小时定时添加每堂课评价记录
     */
    public void autoAddCourseArrangeEvaluate(){
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date endTime=calendar.getTime();
        calendar.add(Calendar.HOUR,-1);
        Date startTime=calendar.getTime();
        List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," endTime>'"+sdf.format(startTime)+"' and endTime<'"+sdf.format(endTime)+"' and status=1");
        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            for(CourseArrange courseArrange:courseArrangeList){
                TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                courseArrange.setCourseId(teachingProgramCourse.getCourseId());
                courseArrange.setCourseName(teachingProgramCourse.getCourseName());
                CourseArrangeEvaluate courseArrangeEvaluate=new CourseArrangeEvaluate(courseArrange);
                this.save(courseArrangeEvaluate);
            }
        }
    }
}
