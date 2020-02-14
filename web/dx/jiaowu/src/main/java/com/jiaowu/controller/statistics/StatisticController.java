package com.jiaowu.controller.statistics;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.thqHour.ThqHourBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.dao.classes.ClassesDao;
import com.jiaowu.dao.classes.ClassesStatisticDao;
import com.jiaowu.dao.course.CourseArrangeDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classes.ClassesDto;
import com.jiaowu.entity.classes.ClassesStatistic;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.thqHour.ThqHour;
import com.jiaowu.util.GenerateSqlUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 教务统计
 * <p>
 * Created by caichenglong on 2017/10/18.
 */
@Controller
public class StatisticController extends BaseController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private ClassesBiz classesBiz;//班级

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private CourseArrangeDao courseArrangeDao;

    @Autowired
    private ClassTypeBiz classTypeBiz;//班型管理

    @Autowired
    private CourseBiz courseBiz;//课程
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    private ClassesStatisticDao classesStatisticDao;

    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ThqHourBiz thqHourBiz;

    @InitBinder({"classes"})
    public void initClasses(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("classes.");
    }


    private static final String ADMIN_PREFIX = "/admin/jiaowu/statistic";
    //统计列表
    private static final String statisticList = "/admin/statistic/statistic_list";
    //教师课程统计
    private static final String statisticCourseList = "/admin/statistic/statistic_course_list";
    //年份班次统计
    private static final String statisticClassNumList = "/admin/statistic/statistic_classes";
    //教师详情
    private static final String statisticInfo = "/admin/statistic/statistic_course_info";


    /**
     * 统计列表
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/statisticList")
    public String StatisticList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("classes") Classes classes) {
        try {
            //2018年11月15日注掉，报错提示：Unknown column 'totalNum' in 'where clause'
            //String whereSql = GenerateSqlUtil.getSql(classes);
            String whereSql = " 1=1";
            String name = classes.getName();
            if (!StringUtils.isEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
            }
            List<Classes> classList = classesBiz.find(pagination, whereSql);
            List<ClassesDto> classesDtos = new ArrayList<>();
            if (ObjectUtils.isNotNull(classList)) {
                for (Classes classes1 : classList) {
                    ClassesDto classesDto = new ClassesDto();

                    Integer maxNum = userBiz.count(" status=1 and classId=" + classes1.getId());
                    if (maxNum == null) {
                        maxNum = 0;
                    }
                    classes1.setMaxNum((long) (maxNum == null ? 0 : maxNum));
                    BeanUtils.copyProperties(classes1, classesDto);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(classesDto.getStartTime());
                    long time1 = cal.getTimeInMillis();
                    cal.setTime(classesDto.getEndTime());
                    long time2 = cal.getTimeInMillis();
                    long between_days = (time2 - time1) / (1000 * 3600 * 24);
                    classesDto.setComparDays(Integer.parseInt(String.valueOf(between_days)));
                    classesDtos.add(classesDto);
                }
            }
            pagination.setRequest(request);
            classesBiz.queryStudentNotReportNum(classList);
            request.setAttribute("classList", classesDtos);
            request.setAttribute("classes", classes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticList;

    }

    /**
     * 查询教师课时统计
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/statisticCourseLesson")
    public String statisticCourseLesson(HttpServletRequest request) {
        try {
            /*String whereSql = " where   c.teacherName=";
            String teacherName = request.getParameter("teacherName");
            if (teacherName != null && teacherName != "") {
                whereSql += "\"" + teacherName + "\"";
            } else {
                whereSql = "";
            }*/
            String whereSql = " where 1=1";
            String teacherName = request.getParameter("teacherName");
            if (teacherName != null && !teacherName.equals("")) {
                whereSql += " and teacherName like '%" + teacherName + "%'";
            }
            String startTime = request.getParameter("startTime");
            if (startTime != null && !startTime.equals("")) {
                whereSql += " and createTime > '" + startTime + "'";
            }
            String endTime = request.getParameter("endTime");
            if (endTime != null && !endTime.equals("")) {
                whereSql += " and createTime < '" + endTime + "'";
            }
            List<CourseArrange> courseArrangeNumList = courseArrangeDao.queryCourseArrange(whereSql);
            // 教师课时 补丁
            List<ThqHour> hours = thqHourBiz.findAll();
            Map<Long, Long> hourMap = hours.stream().collect(Collectors.toMap(ThqHour::getThqId, ThqHour::getHour));
            for (CourseArrange courseArrange : courseArrangeNumList) {
                Long myHour = hourMap.get(courseArrange.getTeacherId());
                myHour = myHour == null ? 0L : myHour;
                courseArrange.setSum(courseArrange.getSum() + myHour);
            }


            String teacherNames = "";
            String hour = "";
            for (CourseArrange courseArrange : courseArrangeNumList) {
                Long teacherId = Long.parseLong(courseArrange.getTeacherId().toString());
                Map<String, String> teacherMap = hrHessianService.queryEmployeeById(teacherId);
                if (teacherMap != null) {
                    String teacherResearchIdString = teacherMap.get("teachingResearchDepartment");
                    if (!StringUtils.isTrimEmpty(teacherResearchIdString)) {
                        courseArrange.setTeacherResearchId(Integer.parseInt(teacherResearchIdString));
                    } else {
                        courseArrange.setTeacherResearchId(null);
                    }
                }
                String name = courseArrange.getTeacherName().replaceAll(",", "、");
                teacherNames += '\"' + name + '\"' + ",";
                hour += courseArrange.getSum() + ",";
            }
            if (!StringUtils.isTrimEmpty(teacherNames)) {
                teacherNames = teacherNames.substring(0, teacherNames.length() - 1);
                request.setAttribute("teacherNames", teacherNames);
            }
            if (!StringUtils.isTrimEmpty(hour)) {
                hour = hour.substring(0, hour.length() - 1);
                request.setAttribute("hour", hour);
            }
            request.setAttribute("courseArrangeNumList", courseArrangeNumList);
            request.setAttribute("teacherName", teacherName);
            request.setAttribute("startTime", startTime);
            request.setAttribute("endTime", endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticCourseList;
    }

    /**
     * 查询教师课时统计导出
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/statisticCourseLessonExcel")
    public void statisticCourseLessonExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String whereSql = " where 1=1";
            String teacherName = request.getParameter("teacherName");
            if (teacherName != null && !teacherName.equals("")) {
                whereSql += " and teacherName like '%" + teacherName + "%'";
            }
            String startTime = request.getParameter("startTime");
            if (startTime != null && !startTime.equals("")) {
                whereSql += " and createTime > '" + startTime + "'";
            }
            String endTime = request.getParameter("endTime");
            if (endTime != null && !endTime.equals("")) {
                whereSql += " and createTime < '" + endTime + "'";
            }
            List<CourseArrange> courseArrangeNumList = courseArrangeDao.queryCourseArrange(whereSql);
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/teacherCourseArrangeNum");
            String expName = "教师课时统计";
            String[] headName = {"教师", "课时数量"};
            List<File> srcfile = courseArrangeBiz.statisticCourseLessonExcel(request, dir, headName, expName, courseArrangeNumList);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(ADMIN_PREFIX + "/chgThqHour/{id}")
    public ModelAndView queryChqThqHour(@PathVariable("id") Long teacherId) {
        ModelAndView mv = new ModelAndView("/admin/statistic/statistic_course_chgThqHour");
        try {
            List<ThqHour> hours = thqHourBiz.getThqHours(teacherId);
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(hours)) {
                mv.addObject("entity", hours.get(0));
            }
            mv.addObject("thqId", teacherId);
        } catch (Exception e) {
            logger.error("StatisticController.queryChqThqHour", e);
        }
        return mv;
    }

    @RequestMapping(ADMIN_PREFIX + "/saveOrUpdateThqHour")
    @ResponseBody
    public Map<String, Object> saveOrUpdateThqHour(ThqHour thqHour) {
        Map<String, Object> objMap;
        try {
            thqHourBiz.saveOrUpd(thqHour);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("StatisticController.saveOrUpdateThqHour", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 查询按照教研处进行统计课时数量
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/statisticCourseByTeacherResearch")
    public ModelAndView statisticCourseByTeacherResearch(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/admin/statistic/statistic_teachersearch_list");
        try {
            String whereSql = " where 1=1";
            String startTime = request.getParameter("startTime");
            if (!StringUtils.isTrimEmpty(startTime)) {
                whereSql += " and createTime > '" + startTime + "'";
            }
            String endTime = request.getParameter("endTime");
            if (!StringUtils.isTrimEmpty(endTime)) {
                whereSql += " and createTime < '" + endTime + "'";
            }

            String dangshi = whereSql + " and teacherResearchId=1";
            String gongguan = whereSql + " and teacherResearchId=2";
            String jingji = whereSql + " and teacherResearchId=3";
            String faxue = whereSql + " and teacherResearchId=4";
            String wenhua = whereSql + " and teacherResearchId=5";
            String malie = whereSql + " and teacherResearchId=6";
            String tongyi = whereSql + " and teacherResearchId=7";

            int dangshiCount = courseArrangeDao.statisticCourseByTeacherResearch(dangshi);
            int gongguanCount = courseArrangeDao.statisticCourseByTeacherResearch(gongguan);
            int jingjiCount = courseArrangeDao.statisticCourseByTeacherResearch(jingji);
            int faxueCount = courseArrangeDao.statisticCourseByTeacherResearch(faxue);
            int wenhuaCount = courseArrangeDao.statisticCourseByTeacherResearch(wenhua);
            int malieCount = courseArrangeDao.statisticCourseByTeacherResearch(malie);
            int tongyiCount = courseArrangeDao.statisticCourseByTeacherResearch(tongyi);
            modelAndView.addObject("dangshiCount", dangshiCount);
            modelAndView.addObject("gongguanCount", gongguanCount);
            modelAndView.addObject("jingjiCount", jingjiCount);
            modelAndView.addObject("faxueCount", faxueCount);
            modelAndView.addObject("wenhuaCount", wenhuaCount);
            modelAndView.addObject("malieCount", malieCount);
            modelAndView.addObject("malieCount", malieCount);
            modelAndView.addObject("tongyiCount", tongyiCount);
            //课时总数
            int total = dangshiCount + gongguanCount + jingjiCount + faxueCount + wenhuaCount + malieCount + tongyiCount;
            modelAndView.addObject("total", total);
            //获取班型未主体班的班次
            List<Classes> classesList = classesBiz.find(null, " status=1 and classTypeId=6");
            String classIds = "";
            if (classesList != null && classesList.size() > 0) {
                for (Classes classes : classesList) {
                    classIds += classes.getId() + ",";
                }
                classIds = classIds.substring(0, classIds.length() - 1);
            } else {
                classIds = "0";
            }
            //主体班总课时
            int dangshiZhuTiCount = courseArrangeDao.statisticCourseByTeacherResearch(dangshi + " and classId in (" + classIds + ")");
            int gongguanZhuTiCount = courseArrangeDao.statisticCourseByTeacherResearch(gongguan + " and classId in (" + classIds + ")");
            int jingjiZhuTiCount = courseArrangeDao.statisticCourseByTeacherResearch(jingji + " and classId in (" + classIds + ")");
            int faxueZhuTiCount = courseArrangeDao.statisticCourseByTeacherResearch(faxue + " and classId in (" + classIds + ")");
            int wenhuaZhuTiCount = courseArrangeDao.statisticCourseByTeacherResearch(wenhua + " and classId in (" + classIds + ")");
            int malieZhuTiCount = courseArrangeDao.statisticCourseByTeacherResearch(malie + " and classId in (" + classIds + ")");
            int tongyiZhuTiCount = courseArrangeDao.statisticCourseByTeacherResearch(tongyi + " and classId in (" + classIds + ")");
            int zhutiTotalCount = dangshiZhuTiCount + gongguanZhuTiCount + jingjiZhuTiCount + faxueZhuTiCount + wenhuaZhuTiCount + malieZhuTiCount + tongyiZhuTiCount;
            modelAndView.addObject("zhutiTotalCount", zhutiTotalCount);
            //教师数量
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(null, " employeeType=1 and status=1 and type=0");
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            int teacherCount = teacherList != null && teacherList.size() > 0 ? teacherList.size() : 0;
            modelAndView.addObject("teacherCount", teacherCount);
            //主体班平均课时
            if (teacherCount == 0) {
                modelAndView.addObject("teacherZhuTiAverage", zhutiTotalCount);
            } else {
                double teacherZhuTiAverage = zhutiTotalCount * 1.00 / teacherCount;
                DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                modelAndView.addObject("teacherZhuTiAverage", decimalFormat.format(teacherZhuTiAverage));
            }

            if (classesList != null && classesList.size() > 0) {
                double jiaoyanZhuTiAverage = zhutiTotalCount * 1.00 / classesList.size();
                DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                modelAndView.addObject("jiaoyanZhuTiAverage", decimalFormat.format(jiaoyanZhuTiAverage));
            } else {
                modelAndView.addObject("jiaoyanZhuTiAverage", 0);
            }

            modelAndView.addObject("startTime", startTime);
            modelAndView.addObject("endTime", endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 详情
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryCourseInfo/{id}")
    public String statisticCourseInfo(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @PathVariable("id") Long id) {
        try {

           /* String className = request.getParameter("className");
            request.setAttribute("className", className);
            if (className != "" && className != null) {
                className = " and cla.name=" + "\"" + className + "\"";
            } else {
                className = "";
            }*/
           /* String where
            pagination.setPageSize(10);
            pagination.setRequest(request);
            List<CourseArrange> courseArrangeList = courseArrangeDao.queryCourseInfo(id.toString(), className, pagination.getCurrentPage(), pagination.getPageSize());

            int count = courseArrangeDao.queryCountCourseInfo(id.toString(), className);
            pagination.init(count, pagination.getPageSize());

            request.setAttribute("courseArrangeList", courseArrangeList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("id", id);*/
            String whereSql = " teacherId=" + id;
            String className = request.getParameter("className");
            if (className != null && !className.equals("")) {
                whereSql += " and className like '%" + className + "%'";
            }
            String startTime = request.getParameter("startTime");
            if (startTime != null && !startTime.equals("")) {
                whereSql += " and createTime > '" + startTime + "'";
            }
            String endTime = request.getParameter("endTime");
            if (endTime != null && !endTime.equals("")) {
                whereSql += " and createTime < '" + endTime + "'";
            }
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(pagination, whereSql);
            if (courseArrangeList != null && courseArrangeList.size() > 0) {
                for (CourseArrange courseArrange : courseArrangeList) {
                    TeachingProgramCourse teachingProgramCourse = teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    if (teachingProgramCourse != null) {
                        courseArrange.setCourseName(teachingProgramCourse.getCourseName());
                    }
                }
            }

            List<ClassesStatistic> classesStatisticList = classesStatisticDao.queryClassPersonNum();
            Map<Long, Integer> map =
                    classesStatisticList.stream()
                            .collect(Collectors.toMap(ClassesStatistic::getClassId, ClassesStatistic::getNum));

            if (courseArrangeList != null) {
                courseArrangeList.forEach(e -> e.setTotalNum(map.get(e.getClassId())));
            }

            request.setAttribute("courseArrangeList", courseArrangeList);
            request.setAttribute("id", id);
            request.setAttribute("className", className);
            request.setAttribute("startTime", startTime);
            request.setAttribute("endTime", endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticInfo;
    }


    /**
     * 教师课时详情导出
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryCourseInfoExcel/{id}")
    public void queryCourseInfoExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("pagination") Pagination pagination, @PathVariable("id") Long id) {
        try {

            String whereSql = " teacherId=" + id;
            String className = request.getParameter("className");
            if (className != null && !className.equals("")) {
                whereSql += " and className like '%" + className + "%'";
            }
            String startTime = request.getParameter("startTime");
            if (startTime != null && !startTime.equals("")) {
                whereSql += " and createTime > '" + startTime + "'";
            }
            String endTime = request.getParameter("endTime");
            if (endTime != null && !endTime.equals("")) {
                whereSql += " and createTime < '" + endTime + "'";
            }
            pagination.setPageSize(10000);
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(pagination, whereSql);
            if (courseArrangeList != null && courseArrangeList.size() > 0) {
                for (CourseArrange courseArrange : courseArrangeList) {
                    TeachingProgramCourse teachingProgramCourse = teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    if (teachingProgramCourse != null) {
                        courseArrange.setCourseName(teachingProgramCourse.getCourseName());
                    }
                }
            }
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/teacherCourseArrangeNumDetail");
            String expName = courseArrangeList.get(0).getTeacherName() + "教师课时统计";
            String[] headName = {"教师", "班次", "课程", "教室", "时间"};
            List<File> srcfile = courseArrangeBiz.queryCourseInfoExcel(request, dir, headName, expName, courseArrangeList);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 统计班级数量
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/statisticClassNum")
    public String statisticClassNum(HttpServletRequest request) {
        try {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);    //获取年

            String years = request.getParameter("year");
            if (years == null || years == "") {
                years = String.valueOf(year);
            }
            request.setAttribute("year", years);


            // 查询一年中每个月的总数
            List<ClassesStatistic> classesStatisticList = classesDao.queryClassesStatisticByYear(years);

            // 1-12月
            List<ClassesStatistic> result = new ArrayList(12);

            for (int i = 0; i < 12; i++) {
                ClassesStatistic n = new ClassesStatistic();
                n.setYear(year);
                n.setMonth(i + 1);
                result.add(n);
            }

            classesStatisticList.forEach(
                    n -> result.set(n.getMonth() - 1, n)
            );


            if (result != null && result.size() > 0) {
                String data = "";
                for (ClassesStatistic n : result) {
                    data += n.getAmount() + ",";
                }
                request.setAttribute("data", data);
            }
            request.setAttribute("classesStatisticList", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticClassNumList;
    }

    /**
     * 班级数量导出
     *
     * @param request
     */
    @RequestMapping(ADMIN_PREFIX + "/statisticClassNumExcel")
    public void statisticClassNumExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);    //获取年

            String years = request.getParameter("year");
            if (years == null || years == "") {
                years = String.valueOf(year);
            }


            // 查询一年中每个月的总数
            List<ClassesStatistic> classesStatisticList = classesDao.queryClassesStatisticByYear(years);

            // 1-12月
            List<ClassesStatistic> result = new ArrayList(12);

            for (int i = 0; i < 12; i++) {
                ClassesStatistic n = new ClassesStatistic();
                n.setYear(year);
                n.setMonth(i + 1);
                result.add(n);
            }

            classesStatisticList.forEach(
                    n -> result.set(n.getMonth() - 1, n)
            );


            if (result != null && result.size() > 0) {
                String data = "";
                for (ClassesStatistic n : result) {
                    data += n.getAmount() + ",";
                }
            }

            String dir = request.getSession().getServletContext().getRealPath("/excelfile/classes");
            String expName = years + "年班次数量";
            String[] headName = {"月份", "数量"};
            List<File> srcfile = classesBiz.classesNumOfOneYear(request, dir, headName, expName, result);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
