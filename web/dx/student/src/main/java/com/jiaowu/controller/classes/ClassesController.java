package com.jiaowu.controller.classes;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.unit.UnitBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.dao.classes.ClassTypeStatisticDao;
import com.jiaowu.dao.classes.ClassesStatisticDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.ClassTypeStatistic;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classes.ClassesStatistic;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.unit.Unit;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 班次Controller
 *
 * @author 李帅雷
 */
@Controller
public class ClassesController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    BaseHessianService baseHessianService;
    @Autowired
    private ClassesStatisticDao classesStatisticDao;
    @Autowired
    private ClassTypeStatisticDao classTypeStatisticDao;
    @Autowired
    private UnitBiz unitBiz;
    @Autowired
    private UserWorkDayDataBiz userWorkDayDataBiz;
    @Autowired
    private HrHessianService hrHessianService;

    private static final String ADMIN_PREFIX = "/admin/jiaowu/class";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/class";

    @InitBinder({"classes"})
    public void initClasses(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("classes.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @RequestMapping("/admin/jiaowu/jiaowuIndex")
    public String jiaowuIndex() {
//        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        List<UserWorkDayData> toDayList = userWorkDayDataBiz.find(null, " workDate like '" + bartDateFormat.format(new Date()).trim() + "'");
//        List<Long> ids = toDayList.stream().map(UserWorkDayData::getId).collect(Collectors.toList());
//        userWorkDayDataBiz.deleteByIds(ids);
        return "/admin/jiaowuIndex";
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到创建班次的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/createClass")
    public String createClass(HttpServletRequest request) {
        try {
            setAttributeClassTypeList(request);
        } catch (Exception e) {
            logger.info("ClassesController.createClass", e);
        }
        return "/admin/class/create_class";
    }


    /**
     * @param request
     * @param classes
     * @return java.util.Map
     * @Description 创建班次
     */
    @RequestMapping(ADMIN_PREFIX + "/doCreateClass")
    @ResponseBody
    public Map<String, Object> doCreateClass(HttpServletRequest request,
                                             @ModelAttribute("classes") Classes classes) {
        Map<String, Object> json = null;
        try {
            json = validateClasses(classes);
            if (json != null) {
                return json;
            }
            ClassType classType = classTypeBiz.findById(classes
                    .getClassTypeId());
            classes.setClassNumber(classesBiz.getClassNumber(classes));
            classes.setClassType(classType.getName());
            classesBiz.save(classes);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            logger.info("ClassesController.doCreateClass", e);
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到更新班次的页面上
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/updateClass")
    public String updateClass(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            Classes classes = classesBiz.findById(id);
            request.setAttribute("classes", classes);
            setAttributeClassTypeList(request);
        } catch (Exception e) {
            logger.info("ClassesController.updateClass", e);
        }
        return "/admin/class/update_class";
    }

    /**
     * @param request
     * @param classes
     * @return java.util.Map
     * @Description 更新班次
     */
    @RequestMapping(ADMIN_PREFIX + "/doUpdateClass")
    @ResponseBody
    public Map<String, Object> doUpdateClass(HttpServletRequest request,
                                             @ModelAttribute("classes") Classes classes) {
        Map<String, Object> json = null;
        try {
            json = validateClasses(classes);
            if (json != null) {
                return json;
            }
            ClassType classType = classTypeBiz.findById(classes
                    .getClassTypeId());
            classes.setClassType(classType.getName());
            classesBiz.update(classes);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 班次列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/classList")
    public String classList(HttpServletRequest request,
                            @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String roleIds = baseHessianService.queryUserRolesByUserId(Long.parseLong(userMap.get("id")));
            if (!StringUtils.isEmpty(roleIds)) {
                if (roleIds.indexOf("27") != -1) {
                    request.setAttribute("unit", "true");
                }
            }
            Classes classes = new Classes();
            String whereSql = classesBiz.addCondition(request, classes);
            pagination.setRequest(request);
            List<Classes> classList = classesBiz.find(pagination, whereSql);
            List<ClassesStatistic> classesStatisticList = classesStatisticDao.queryClassPersonNum("");

            if (ObjectUtils.isNotNull(classList) && ObjectUtils.isNotNull(classesStatisticList)) {
                for (Classes classes1 : classList) {
                    for (ClassesStatistic classesStatistic : classesStatisticList) {
                        if (classes1.getId().equals(classesStatistic.getClassId())) {
                            classes1.setStudentTotalNum(Long.parseLong(String.valueOf(classesStatistic.getNum())));
                        }
                    }
                }
            }
            classesBiz.queryStudentNotReportNum(classList);
            request.setAttribute("classList", classList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("classes", classes);
            setAttributeClassTypeList(request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/class_list";
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的班次列表
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/classListForSelect")
    public String classListForSelect(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination) {
        try {
            Classes classes = new Classes();
            String whereSql = classesBiz.addCondition(request, classes);
            pagination.setCurrentUrl(classesBiz.getCurrentUrl(request, pagination, classes));
            List<Classes> classList = classesBiz.find(pagination, whereSql);
            request.setAttribute("classList", classList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("classes", classes);
            setAttributeClassTypeList(request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/class_list_for_select";
    }

    /**
     * @param request
     * @return java.util.Map
     * @Description 删除班次
     */
    @RequestMapping(ADMIN_PREFIX + "/deleteClass")
    @ResponseBody
    public Map<String, Object> deleteClass(HttpServletRequest request,
                                           @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Classes classes = classesBiz.findById(id);
            classes.setStatus(0);
            classesBiz.update(classes);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param classTypeId
     * @return java.util.Map
     * @Description 通过班型ID获取班次列表
     */
    @RequestMapping(ADMIN_PREFIX + "/getClassListByClassType")
    @ResponseBody
    public Map<String, Object> getClassListByClassType(
            HttpServletRequest request,
            @RequestParam("classTypeId") Long classTypeId) {
        Map<String, Object> json = null;
        try {
            List<Classes> classList = classesBiz.find(null, " status=1 and classTypeId="
                    + classTypeId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    classList);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 班次课表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/classActivity")
    public String classActivity(HttpServletRequest request,
                                @ModelAttribute("pagination") Pagination pagination, @RequestParam("classId") Long classId) {
        try {

            String whereSql = " classId=" + classId;
            CourseArrange courseArrange = new CourseArrange();
            courseArrange.setClassId(classId);
            whereSql += classesBiz.addCondition(request, courseArrange);
            pagination.setRequest(request);
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(
                    pagination, whereSql);
            classesBiz.setCourseInfoAndDeleteUselessCourseArrange(courseArrangeList);
            request.setAttribute("courseArrangeList", courseArrangeList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("courseArrange", courseArrange);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/class/class_activity";
    }

    /**
     * 验证班次信息
     *
     * @param classes
     * @return
     */
    public Map<String, Object> validateClasses(Classes classes) {
        Map<String, Object> errorInfo = null;
        if (StringUtils.isTrimEmpty(classes.getName())) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "名称不能为空", null);
            return errorInfo;
        }
        if (classes.getTeacherId() == null || classes.getTeacherId() <= 0) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "班主任不能为空", null);
            return errorInfo;
        }
        if (classes.getTeacherId().equals(classes.getDeputyTeacherId())) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "班主任与副班主任不能为同一人", null);
        }
        if (classes.getStartTime() == null) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "开始时间不能为空", null);
            return errorInfo;
        }
        if (classes.getEndTime() == null) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "结束时间不能为空", null);
            return errorInfo;
        }
        if (classes.getStartTime().getTime() >= classes.getEndTime().getTime()) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "开始时间不能大于结束时间", null);
            return errorInfo;
        }
        if (classes.getMaxNum() == null || classes.getMaxNum().equals(0L)) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "最大人数填写不正确", null);
        }
        return null;
    }

    /**
     * 向页面返回班型列表
     *
     * @param request
     */
    public void setAttributeClassTypeList(HttpServletRequest request) {
        List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id desc");
        request.setAttribute("classTypeList", classTypeList);
    }


    @RequestMapping("/queryList")
    @ResponseBody
    public Map<String, Object> queryList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {

        Map<String, Object> json = null;
        try {
            //封装查询参数
            Classes classes = new Classes();
            String whereSql = classesBiz.addCondition(request, classes);
            pagination.setRequest(request);
            //执行查询
            List<Classes> classList = classesBiz.find(null, whereSql);
            //返回结果
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, classList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }


    @RequestMapping("file/download")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) {
        //文件下载
        downFile(response, request);
    }


    public void downFile(HttpServletResponse response, HttpServletRequest request) {

        /*        HttpServletResponse response = this.getResponse();*/
        try {
            // 清空response
            response.reset();
            //设置输出的格式
            response.setContentType("multipart/form-data");// 设置为下载application/x-download
            /*response.addHeader("content-type ","application/x-msdownload");
            response.setContentType("application/octet-stream");*/
            response.setHeader("Content-Disposition", "attachment; filename=" + transCharacter(request, "a.pdf"));//设定输出文件头
            response.addHeader("Content-Length", "1024");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream("c:/input/download.pdf"));
            ServletOutputStream toClient = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = fis.read(buffer)) != -1) {
                toClient.write(buffer, 0, n);
                toClient.flush();
            }
            fis.close();
            //输出文件
            toClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 根据不同的浏览器转字符编码
     *
     * @param request
     * @param str
     * @return
     * @throws Exception
     */
    private String transCharacter(HttpServletRequest request, String str) throws Exception {
        if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0) {
            return URLEncoder.encode(str, "UTF-8");
        } else if (request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") > 0) {
            return new String(str.getBytes("UTF-8"), "ISO8859-1");
        }
        return new String(str.getBytes("UTF-8"), "ISO8859-1");
    }

    /**
     * 查询各个班次的报名人数
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryClassPersonNum")
    public ModelAndView queryClassPersonNum(HttpServletRequest request, @RequestParam(value = "type", required = false) Long type) {
        ModelAndView mv = new ModelAndView("/admin/class/class_statistic_list");
        try {
            String whereSql = " and 1=1";
            if (type != null) {
                whereSql += " and type.id=" + type;
            }
            List<ClassesStatistic> classesStatisticList = classesStatisticDao.queryClassPersonNum(whereSql);
            mv.addObject("classesStatisticList", classesStatisticList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 查询各个班次的报名人数
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryClassTypePersonNum")
    public ModelAndView queryClassTypePersonNum(HttpServletRequest request, @RequestParam(value = "id", required = false) Long type) {
        ModelAndView mv = new ModelAndView("/admin/class/classType_statistic_list");
        try {
            List<ClassTypeStatistic> classTypeStatisticList = classTypeStatisticDao.queryClassTypeCount(new ClassTypeStatistic());
            mv.addObject("classTypeStatisticList", classTypeStatisticList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 查询各个班次的报名人数
     *
     * @return
     */
    @RequestMapping("/admin/jiaowu/class/queryUnitPersonNum")
    public ModelAndView queryUnitPersonNum(HttpServletRequest request, @RequestParam("classId") String classId, @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/admin/class/class_unit_statistic_list");
        try {
            pagination.setRequest(request);
            Classes classes = classesBiz.findById(Long.parseLong(classId));
            mv.addObject("classes", classes);

            List<ClassesStatistic> classesStatisticList = classesStatisticDao.queryUnitPersonNum(classId);

            List<ClassesStatistic> classesTypeStatisticList = classesStatisticDao.queryUnitClassTypeNum(classes.getClassTypeId().toString());


            List<Unit> unitList = unitBiz.find(pagination, "1=1");
            if (ObjectUtils.isNotNull(classesStatisticList) && ObjectUtils.isNotNull(unitList) && ObjectUtils.isNotNull(classesTypeStatisticList)) {
                for (Unit c : unitList) {
                    for (ClassesStatistic statistic : classesStatisticList) {
                        if (c.getId().equals(statistic.getId())) {
                            c.setNum(statistic.getNum());
                        }
                    }
                }

                for (Unit u : unitList) {
                    for (ClassesStatistic statistic : classesTypeStatisticList) {
                        if (u.getId().equals(Long.parseLong(String.valueOf(statistic.getUnitId())))) {
                            u.setClassNum(statistic.getNum());
                        }
                    }
                }

            }
            mv.addObject("unitList", unitList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 班次列表(供批量报名使用)
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/classListForBatchSign")
    public String classListForBatchSign(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination) {
        try {
            Classes classes = new Classes();
            String whereSql = classesBiz.addCondition(request, classes);
            pagination.setRequest(request);
            List<Classes> classList = classesBiz.find(pagination, whereSql);
            classesBiz.queryStudentNotReportNum(classList);
            request.setAttribute("classList", classList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("classes", classes);
            setAttributeClassTypeList(request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/class_list_for_batch_sign";
    }

    /**
     * 跳转到设置班主任的页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateClassLeaderOfClass")
    public String toUpdateClassLeaderOfClass(HttpServletRequest request, Long id) {
        try {
            Classes classes = classesBiz.findById(id);
            request.setAttribute("classes", classes);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/update_classleader_of_class";
    }

    /**
     * @param request
     * @param classes
     * @return java.util.Map
     * @Description 设置班主任
     */
    @RequestMapping(ADMIN_PREFIX + "/updateClassLeaderOfClass")
    @ResponseBody
    public Map<String, Object> updateClassLeaderOfClass(HttpServletRequest request,
                                                        @ModelAttribute("classes") Classes classes) {
        Map<String, Object> json = null;
        try {
            json = validateClasses(classes);
            if (json != null) {
                return json;
            }
            //修改班主任信息
            ClassType classType = classTypeBiz.findById(classes.getClassTypeId());
            classes.setClassType(classType.getName());
            classesBiz.updateClasses(classes);

            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 查看当前登录用户所带班级
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/myClassList")
    public String myClassList(HttpServletRequest request) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            //查询用户角色
            Long userId = SysUserUtils.getLoginSysUserId(request);
            Long teacherId = hrHessianService.getEmployeeBySysUserId(userId);
            List<Classes> classList = classesBiz.find(null, " teacherId=" + teacherId+" or deputyTeacherId="+teacherId);
            request.setAttribute("classList", classList);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/myClass_list";
    }
}
