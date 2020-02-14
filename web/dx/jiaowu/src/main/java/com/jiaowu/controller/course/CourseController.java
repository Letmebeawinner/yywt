package com.jiaowu.controller.course;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.courseType.CourseTypeBiz;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.courseType.CourseType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 课程Controller
 *
 * @author 李帅雷
 */
@Controller
public class CourseController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseBiz courseBiz;
    @Autowired
    private CourseTypeBiz courseTypeBiz;
    @Autowired
    private HrHessianService hrHessianService;

    private static final String ADMIN_PREFIX = "/admin/jiaowu/course";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/course";

    @InitBinder({"course"})
    public void initCourse(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("course.");
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到创建课程的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateCourse")
    public String toCreateCourse(HttpServletRequest request) {
        try {
            courseTypeBiz.setAttributeCourseTypeList(request);
        } catch (Exception e) {
            logger.info("CourseController.toCreateCourse", e);
        }
        return "/admin/course/create_course";
    }

    /**
     * @param request
     * @param course
     * @return java.util.Map
     * @Description 创建课程
     */
    @RequestMapping(ADMIN_PREFIX + "/createCourse")
    @ResponseBody
    public Map<String, Object> createCourse(HttpServletRequest request, @ModelAttribute("course") Course course) {
        Map<String, Object> json = null;
        try {
            json = validateCourse(course);
            if (json != null) {
                return json;
            }
            CourseType courseType = courseTypeBiz.findById(course.getCourseTypeId());
            course.setCourseTypeName(courseType.getName());
            courseBiz.save(course);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.info("CourseController.createCourse", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param id
     * @return java.lang.String
     * @Description 跳转到修改课程的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateCourse")
    public String toUpdateCourse(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            courseTypeBiz.setAttributeCourseTypeList(request);
            Course course = courseBiz.findById(id);
            course.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(course.getTeacherId()));
            request.setAttribute("course", course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/course/update_course";
    }

    /**
     * @param request
     * @param course
     * @return java.util.Map
     * @Description 修改课程
     */
    @RequestMapping(ADMIN_PREFIX + "/updateCourse")
    @ResponseBody
    public Map<String, Object> updateCourse(HttpServletRequest request, @ModelAttribute("course") Course course) {
        Map<String, Object> json = null;
        try {
            validateCourse(course);
            CourseType courseType = courseTypeBiz.findById(course.getCourseTypeId());
            course.setCourseTypeName(courseType.getName());
            courseBiz.update(course);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 课程列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/courseList")
    public String courseList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            courseTypeBiz.setAttributeCourseTypeList(request);
            String whereSql = " status=1";
            Course course = new Course();
            whereSql += courseBiz.addCondition(request, course);
            pagination.setRequest(request);
            List<Course> courseList = courseBiz.find(pagination, whereSql);
            if (courseList != null && courseList.size() > 0) {
                for (Course tempCourse : courseList) {
                    tempCourse.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(tempCourse.getTeacherId()));
                }
            }
            request.setAttribute("courseList", courseList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("course", course);
        } catch (Exception e) {
            logger.info("CourseController.courseList", e);
        }
        return "/admin/course/course_list";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的课程列表
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/courseListForSelect")
    public String courseListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            courseTypeBiz.setAttributeCourseTypeList(request);
            String whereSql = " status=1";
            Course course = new Course();
            whereSql += courseBiz.addCondition(request, course);
            pagination.setCurrentUrl(courseBiz.getCurrentUrl(request, pagination, course));
            List<Course> courseList = courseBiz.find(pagination, whereSql);
            if (courseList != null && courseList.size() > 0) {
                for (Course tempCourse : courseList) {
                    tempCourse.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(tempCourse.getTeacherId()));
                }
            }
            request.setAttribute("courseList", courseList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("course", course);
        } catch (Exception e) {
            logger.info("CourseController.courseList", e);
        }
        return "/admin/course/course_list_for_select";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的课程列表, 多选
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/courseListForMultiSelect")
    public String courseListForMultiSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            courseTypeBiz.setAttributeCourseTypeList(request);
            String whereSql = " status=1";
            Course course = new Course();
            whereSql += courseBiz.addCondition(request, course);
            pagination.setCurrentUrl(courseBiz.getCurrentUrl(request, pagination, course));
            List<Course> courseList = courseBiz.find(pagination, whereSql);
            if (courseList != null && courseList.size() > 0) {
                for (Course tempCourse : courseList) {
                    tempCourse.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(tempCourse.getTeacherId()));
                }
            }
            request.setAttribute("courseList", courseList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("course", course);
        } catch (Exception e) {
            logger.info("CourseController.courseList", e);
        }
        return "/admin/course/course_list_for_multiselect";
    }

    /**
     * @param request
     * @return java.util.Map
     * @Description 删除课程
     */
    @RequestMapping(ADMIN_PREFIX + "/delCourse")
    @ResponseBody
    public Map<String, Object> delCourse(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Course course = courseBiz.findById(id);
            course.setStatus(0);
            courseBiz.update(course);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.info("CourseController.courseList", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证课程
     *
     * @param course
     * @return
     */
    public Map<String, Object> validateCourse(Course course) {
        Map<String, Object> error = null;
        if (StringUtils.isTrimEmpty(course.getName())) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空", null);
            return error;
        }
		/*if(course.getTeacherId()==null||course.getTeacherId()<=0){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "教师不能为空", null);
            return error;
        }*/
        if (course.getTeacherId() == null || course.getTeacherId().equals("")) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "教师不能为空", null);
            return error;
        }
        if (course.getCourseTypeId() == null || course.getCourseTypeId() <= 0) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "课程类别不能为空", null);
            return error;
        }
		/*if(course.getHour()==null){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "学时不能为空", null);
            return error;
		}
		if(course.getHour().equals(0f)){
			error=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "学时不能为0", null);
            return error;
		}*/
        return error;
    }

    /**
     * 跳转导入课程页面
     *
     * @param request
     * @return
     * @author 王赛
     */
    @RequestMapping(ADMIN_PREFIX + "/toImportExcel")
    public String toImportExcel(HttpServletRequest request) {
        return "/admin/course/batch_import_course";
    }

    /**
     * 批量导入课程
     *
     * @param request
     * @param myFile
     * @return
     * @author 王赛
     */
    @RequestMapping(ADMIN_PREFIX + "/batchCourse")
    public String batchSysUser(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            String errorInfo = this.batchImportCourse(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            request.setAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/course/batch_import_course";
    }

    public String batchImportCourse(MultipartFile myFile, HttpServletRequest request) throws Exception {
        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                //课程名称
                String courseName = getCellValue(row.getCell((short) 0));
                if (StringUtils.isTrimEmpty(courseName)) {
                    msg.append("第" + i + "行课程名称不能为空;");
                    continue;
                }
                //课程类别
                String courseType = getCellValue(row.getCell((short) 1));
                if (StringUtils.isTrimEmpty(courseType)) {
                    msg.append("第" + i + "行课程类别不能为空;");
                    continue;
                }
                //判断课程类别是否存在
                List<CourseType> courseTypeList = courseTypeBiz.find(null, "status=1 and name = '" + courseType + "'");
                if (ObjectUtils.isNull(courseTypeList)) {
                    msg.append("第" + i + "行课程类别不存在;");
                    continue;
                }
                //课程讲师
                String teacher = getCellValue(row.getCell((short) 2));
                if (StringUtils.isTrimEmpty(teacher)) {
                    msg.append("第" + i + "行讲师不能为空;");
                    continue;
                }
                String notTeacher = "";
                String teacherIds = "";
                String teacherName[] = teacher.split("@");
                for (String str : teacherName) {
                    Map<String, Object> map = hrHessianService.getEmployeeListBySql(null, " status!=2 and (type=2 or type=3) and name = '" + str + "'");
                    List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
                    if (teacherList != null && teacherList.size() > 0) {
                        for (Map<String, String> t : teacherList) {
                            teacherIds += t.get("id") + ",";
                        }
                    } else {
                        notTeacher += str + ",";
                    }
                }
                if (notTeacher != "") {
                    msg.append("第" + i + "行" + notTeacher.substring(0, notTeacher.length() - 1) + "讲师不存在;");
                    continue;
                }
                //备注
                String note = getCellValue(row.getCell((short) 3));
                //年份
                String year = getCellValue(row.getCell((short) 4));
                //判断年份是否正确
                if(StringUtils.isTrimEmpty(year)){
                    msg.append("第" + i + "行" + "年份不能为空;");
                    continue;
                }
                if(year.length()>4 || year.length()<4){
                    msg.append("第" + i + "行" + "年份填写不正确;");
                    continue;
                }
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(year);
                if(!isNum.matches() ){
                    msg.append("第" + i + "行" + "年份填写不正确;");
                    continue;
                }
                if(!year.startsWith("20")){
                    msg.append("第" + i + "行" + "年份填写不正确;");
                    continue;
                }
                //季节
                String season = getCellValue(row.getCell((short) 5));
                if(StringUtils.isTrimEmpty(season)){
                    msg.append("第" + i + "行" + "季节不能为空;");
                    continue;
                }
                if(!"1".equals(season) && !"2".equals(season)){
                    msg.append("第" + i + "行" + "季节填写不正确;");
                    continue;
                }

                List<Course> courseList = courseBiz.find(null,"status = 1 and name = '"+courseName+"' and courseTypeName ='"+courseType+"' and teacherId ='"+teacherIds+"'");
                if(ObjectUtils.isNotNull(courseList)){
                    msg.append("第" + i + "行课程已存在;");
                    continue;
                }

                Course course = new Course();
                course.setName(courseName);
                course.setTeacherId(teacherIds);
                course.setCourseTypeId(courseTypeList.get(0).getId());
                course.setCourseTypeName(courseTypeList.get(0).getName());
                course.setCreateTime(new Timestamp(System.currentTimeMillis()));
                course.setNote(note);
                course.setYear(year);
                course.setSeason(season);
                courseBiz.save(course);
            }
        }
        return msg.toString();
    }

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
}
