package com.jiaowu.biz.classes;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.dao.classes.ClassesDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classes.ClassesStatistic;
import com.jiaowu.entity.classes.ClassesTeacherRecord;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.user.User;
import com.jiaowu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ClassesBiz extends BaseBiz<Classes, ClassesDao> {

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private ClassesTeacherRecordBiz classesTeacherRecordBiz;

    /**
     * 动态获取班次列表中每个班次的未报到人数
     *
     * @param classesList
     */
    public void queryStudentNotReportNum(List<Classes> classesList) {
        if (classesList != null && classesList.size() > 0) {
            for (Classes classes : classesList) {
                List<User> userList = userBiz.find(null,
                        " status=2 and classId=" + classes.getId());
                if (userList != null) {
                    classes.setStudentNotReportNum((long) userList.size());
                } else {
                    classes.setStudentNotReportNum(0L);
                }
            }
        }
    }

    /**
     * 查询班次个数
     *
     * @return
     */
    public int queryClassList() {
        int classNum = this.count("  1=1");
        return classNum;
    }


    /**
     * 获取班次编号
     *
     * @param
     * @param classes
     * @return
     */
    public String getClassNumber(Classes classes) {

        List<Classes> classesList = this.find(null, " status=1");
        if (classesList != null && classesList.size() > 0) {
            Long classNumber = (long) classesList.size() + 1;
            if (classNumber < 10) {
                return "0" + classNumber;
            }
            return "" + classNumber;
        } else {
            return "01";
        }
    }

    /**
     * 增加sql的条件,向classes设置属性,以用于在界面上显示.
     *
     * @param request
     * @param classes
     * @return
     */
    public String addCondition(HttpServletRequest request, Classes classes) {
        String whereSql = " status=1";
        String classTypeId = request.getParameter("classTypeId");
        if (!StringUtils.isTrimEmpty(classTypeId)
                && Long.parseLong(classTypeId) > 0) {
            whereSql += " and classTypeId=" + classTypeId;
            classes.setClassTypeId(Long.parseLong(classTypeId));
        }
        String className = request.getParameter("className");
        if (!StringUtils.isTrimEmpty(className)) {
            whereSql += " and name like '%" + className + "%'";
            classes.setName(className);
        }
        String classNumber = request.getParameter("classNumber");
        if (!StringUtils.isTrimEmpty(classNumber)) {
            whereSql += " and classNumber like '%" + classNumber + "%'";
            classes.setClassNumber(classNumber);
        }
        String teacherId = request.getParameter("teacherId");
        if (!StringUtils.isTrimEmpty(teacherId)
                && Long.parseLong(teacherId) > 0) {
            whereSql += " and teacherId=" + teacherId;
            classes.setTeacherId(Long.parseLong(teacherId));
            classes.setTeacherName(request.getParameter("teacherName"));
        }
        return whereSql;
    }

    /**
     * 增加sql的条件,向courseArrange设置属性,以用于在页面上显示.
     *
     * @param request
     * @param courseArrange
     * @return
     */
    public String addCondition(HttpServletRequest request,
                               CourseArrange courseArrange) {
        String whereSql = "";
        try {
            SimpleDateFormat cstFormater = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = request.getParameter("startTime");
            if (!StringUtils.isTrimEmpty(startTime)) {
                if (startTime.contains("CST")) {
                    startTime = sdf.format(cstFormater.parse(startTime));
                }
                whereSql += " and startTime > '" + startTime + "'";
                courseArrange.setStartTime(sdf.parse(startTime));
            }
            String endTime = request.getParameter("endTime");
            if (!StringUtils.isTrimEmpty(endTime)) {
                if (endTime.contains("CST")) {
                    endTime = sdf.format(cstFormater.parse(endTime));
                }
                whereSql += " and endTime < '" + endTime + "'";
                courseArrange.setEndTime(sdf.parse(endTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return whereSql;
    }

    /**
     * 弹出框页面调用,获取页面的路径.
     *
     * @param request
     * @param pagination
     * @param classes
     * @return
     */
    public String getCurrentUrl(HttpServletRequest request,
                                Pagination pagination, Classes classes) {
        StringBuffer sb = request.getRequestURL().append(
                "?pagination.currentPage=" + pagination.getCurrentPage());
        if (StringUtil.biggerThanZero(classes.getClassTypeId())) {
            sb.append("&classTypeId=" + classes.getClassTypeId());
        }
        if (!StringUtils.isTrimEmpty(classes.getClassNumber())) {
            sb.append("&classNumber=" + classes.getClassNumber());
        }
        if (StringUtil.biggerThanZero(classes.getTeacherId())) {
            sb.append("&teacherId=" + classes.getTeacherId());
        }
        return sb.toString();
    }

    /**
     * 为CourseArrange列表设置课程ID和名称
     *
     * @param courseArrangeList
     */
    public List<CourseArrange> setCourseInfoAndDeleteUselessCourseArrange(
            List<CourseArrange> courseArrangeList) {
        List<CourseArrange> returnCourseArrangeList = new LinkedList<CourseArrange>();
        if (courseArrangeList != null && courseArrangeList.size() > 0) {
            for (CourseArrange courseArrange : courseArrangeList) {
                TeachingProgramCourse teachingProgramCourse = teachingProgramCourseBiz
                        .findById(courseArrange.getTeachingProgramCourseId());
                if (teachingProgramCourse != null) {
                    courseArrange.setCourseId(teachingProgramCourse
                            .getCourseId());
                    courseArrange.setCourseName(teachingProgramCourse
                            .getCourseName());
                    returnCourseArrangeList.add(courseArrange);
                } else {
                    courseArrangeBiz.deleteById(courseArrange.getId());
                }
            }
        }
        return returnCourseArrangeList;
    }

    //修改班次数据
    public void updateClassee(Classes classes, Long classId) {
        String whrereSql = " id=" + classId;

        this.updateByStrWhere(classes, whrereSql);
    }

    /**
     * 导出班次数量
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @param result
     * @return
     * @throws Exception
     */
    public List<File> classesNumOfOneYear(HttpServletRequest request, String dir, String[] headName, String expName, List<ClassesStatistic> result) throws Exception {
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        List<List<String>> all = new LinkedList<List<String>>();
        for (ClassesStatistic classesStatistic : result) {
            List<String> list = new LinkedList<String>();
            list.add(classesStatistic.getMonth() + "");
            list.add((int) classesStatistic.getAmount() + "");
            all.add(list);
        }
        File file = FileExportImportUtil.createExcel(headName, all, expName, dir);
        srcfile.add(file);
        return srcfile;
    }

    /**
     * 修改班次信息，记录班主任历史
     *
     * @param afterClasses
     * @return
     */
    public void updateClasses(Classes afterClasses) {
        Classes beginClasses = this.findById(afterClasses.getId());
        if (!StringUtils.isEmpty(beginClasses.getTeacherId()) && beginClasses.getEndTime().getTime() > new Date().getTime()) {
            //添加班主任历史记录
            if (StringUtils.isEmpty(afterClasses.getTeacherId()) || beginClasses.getTeacherId().longValue() != afterClasses.getTeacherId().longValue()) {
                ClassesTeacherRecord classesTeacherRecord = new ClassesTeacherRecord();
                classesTeacherRecord.setClassesId(beginClasses.getId());
                classesTeacherRecord.setClassesName(beginClasses.getName());
                classesTeacherRecord.setTeacherId(beginClasses.getTeacherId());
                classesTeacherRecord.setTeacherName(hrHessianService.queryEmployeeNameById(beginClasses.getTeacherId()));
                classesTeacherRecord.setType(1);
                classesTeacherRecordBiz.save(classesTeacherRecord);
            }
        }

        if (!StringUtils.isEmpty(beginClasses.getDeputyTeacherId()) && beginClasses.getEndTime().getTime() > new Date().getTime()) {
            //添加副班主任历史记录
            if (StringUtils.isEmpty(afterClasses.getDeputyTeacherId()) || beginClasses.getDeputyTeacherId().longValue() != afterClasses.getDeputyTeacherId().longValue()) {
                ClassesTeacherRecord classesTeacherRecord = new ClassesTeacherRecord();
                classesTeacherRecord.setClassesId(beginClasses.getId());
                classesTeacherRecord.setClassesName(beginClasses.getName());
                classesTeacherRecord.setTeacherId(beginClasses.getDeputyTeacherId());
                classesTeacherRecord.setTeacherName(hrHessianService.queryEmployeeNameById(beginClasses.getDeputyTeacherId()));
                classesTeacherRecord.setType(2);
                classesTeacherRecordBiz.save(classesTeacherRecord);
            }
        }

        this.update(afterClasses);
    }
}
