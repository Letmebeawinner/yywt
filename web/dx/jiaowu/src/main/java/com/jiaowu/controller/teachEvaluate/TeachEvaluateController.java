package com.jiaowu.controller.teachEvaluate;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.course.CourseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.teachEvaluate.TeachEvaluateBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.teachEvaluate.TeachEvaluate;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by 李帅雷 on 2017/8/28.
 */
@Controller
public class TeachEvaluateController extends BaseController {
    private static Logger logger = LoggerFactory
            .getLogger(TeachEvaluateController.class);
    private static final String ADMIN_PREFIX = "/admin/jiaowu/teachEvaluate";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/teachEvaluate";
    @Autowired
    private TeachEvaluateBiz teachEvaluateBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private CourseBiz courseBiz;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    private ClassesBiz classesBiz;

    @InitBinder({"teachEvaluate"})
    public void initBinderTeachEvaluate(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("teachEvaluate.");
    }

    /**
     * 跳转到填写教学质量评估的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/toAddTeachEvaluate")
    public String toAddTeachEvaluate(HttpServletRequest request, Long courseArrangeId) {
        try {
            CourseArrange courseArrange = courseArrangeBiz.findById(courseArrangeId);
            TeachingProgramCourse teachingProgramCourse = teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
            Course course = courseBiz.findById(teachingProgramCourse.getCourseId());
            course.setTeacherName(courseBiz.getTeacherNamesByTeacherIds(course.getTeacherId()));
            request.setAttribute("course", course);
            request.setAttribute("createTime", new Date());
            Map<String, String> teacherMap = hrHessianService.queryEmployeeById(courseArrange.getTeacherId());
            request.setAttribute("teacherId", courseArrange.getTeacherId());
            request.setAttribute("teacherName", teacherMap.get("name"));
            request.setAttribute("courseArrangeId", courseArrangeId);
            /*Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            Course course=courseBiz.findById(courseId);
            request.setAttribute("course",course);
            request.setAttribute("createTime",new Date());
           *//* Map<String,String> teacherMap=hrHessianService.queryEmployeeById(course.getTeacherId());
            request.setAttribute("teacherName",teacherMap.get("name"));*//*
            request.setAttribute("teacherId",1);
            request.setAttribute("teacherName","王梅");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/create_teachEvaluate";
    }

    /**
     * 添加教学质量评估记录
     *
     * @param teachEvaluate
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/addTeachEvaluate")
    @ResponseBody
    public Map<String, Object> addTeachEvaluate(HttpServletRequest request, TeachEvaluate teachEvaluate) {
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (!userMap.get("userType").equals("3")) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限填写教学质量评估表",
                        null);
            }
            teachEvaluate.setCreateTime(new Timestamp(System.currentTimeMillis()));
            User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
            /*List<TeachEvaluate> teachEvaluateList=teachEvaluateBiz.find(null," status=1 and userId="+user.getId()+" and courseId="+teachEvaluate.getCourseId()+" and to_days(createTime)=to_days('"+teachEvaluate.getCreateTime()+"')");
            if(teachEvaluateList!=null&&teachEvaluateList.size()>0){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "该课程您已填写过教学质量评估表,不能重复提交。",
                        null);
            }*/
            if (teachEvaluate.getTotal() > 100) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请正确填写分数",
                        null);
            }
            teachEvaluate.setClassId(user.getClassId());
            teachEvaluate.setUserId(user.getId());
            teachEvaluate.setUserName(user.getName());
            teachEvaluateBiz.save(teachEvaluate);
            json = this.resultJson(ErrorCode.SUCCESS, "提交成功",
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
     * @Description 教学质量评估列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/teachEvaluateList")
    public String teachEvaluateList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {

            List<TeachEvaluate> teachEvaluateList = null;
            String whereSql = " status=1";
            TeachEvaluate teachEvaluate = new TeachEvaluate();
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId)) {
                whereSql += " and classId=" + classId;
                teachEvaluate.setClassId(Long.parseLong(classId));
            }
            pagination.setRequest(request);
            teachEvaluateList = teachEvaluateBiz.find(pagination, whereSql);
            if (teachEvaluateList != null && teachEvaluateList.size() > 0) {
                for (TeachEvaluate tempTeachEvaluate : teachEvaluateList) {
                    Classes classes = classesBiz.findById(tempTeachEvaluate.getClassId());
                    if (classes != null) {
                        tempTeachEvaluate.setClassName(classes.getName());
                    }
                    Map<String, String> teacherMap = hrHessianService.queryEmployeeById(tempTeachEvaluate.getTeacherId());
                    if (teacherMap != null) {
                        tempTeachEvaluate.setTeacherName(teacherMap.get("name"));
                    }
                }
            }
            request.setAttribute("teachEvaluateList", teachEvaluateList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teachEvaluate", teachEvaluate);
            request.setAttribute("className", request.getParameter("className"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/teachEvaluate_list";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 教学质量评估列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/teachEvaluateAverageList")
    public String teachEvaluateAverageList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<TeachEvaluate> teachEvaluateList = null;
            String whereSql = " status=1";
            TeachEvaluate teachEvaluate = new TeachEvaluate();
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId)) {
                whereSql += " and classId=" + classId;
                teachEvaluate.setClassId(Long.parseLong(classId));
            }
            whereSql += " group by  classId";
            pagination.setRequest(request);
            teachEvaluateList = teachEvaluateBiz.find(pagination, whereSql);
            if (teachEvaluateList != null && teachEvaluateList.size() > 0) {
                for (TeachEvaluate tempTeachEvaluate : teachEvaluateList) {
                    tempTeachEvaluate.setAverage(teachEvaluateBiz.getAverageNum(tempTeachEvaluate.getClassId()));
                    Classes classes = classesBiz.findById(tempTeachEvaluate.getClassId());
                    if (classes != null) {
                        tempTeachEvaluate.setClassName(classes.getName());
                    }
                    Map<String, String> teacherMap = hrHessianService.queryEmployeeById(tempTeachEvaluate.getTeacherId());
                    if (teacherMap != null) {
                        tempTeachEvaluate.setTeacherName(teacherMap.get("name"));
                    }
                }
            }
            request.setAttribute("teachEvaluateList", teachEvaluateList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teachEvaluate", teachEvaluate);
            request.setAttribute("className", request.getParameter("className"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/teachEvaluate_average_list";
    }

    /**
     * 查看某教学质量评估
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryTeachEvaluate")
    public String queryTeachEvaluate(HttpServletRequest request, Long id) {
        try {
            TeachEvaluate teachEvaluate = teachEvaluateBiz.findById(id);
            request.setAttribute("teachEvaluate", teachEvaluate);
            CourseArrange courseArrange = courseArrangeBiz.findById(teachEvaluate.getCourseArrangeId());
            Map<String, String> teacherMap = hrHessianService.queryEmployeeById(courseArrange.getTeacherId());
            request.setAttribute("teacherId", courseArrange.getTeacherId());
            request.setAttribute("teacherName", teacherMap.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/queryTeachEvaluate";
    }

    /**
     * 修改某教学质量评估
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateTeachEvaluate")
    public String toUpdateTeachEvaluate(HttpServletRequest request, Long id) {
        try {
            TeachEvaluate teachEvaluate = teachEvaluateBiz.findById(id);
            request.setAttribute("teachEvaluate", teachEvaluate);
            CourseArrange courseArrange = courseArrangeBiz.findById(teachEvaluate.getCourseArrangeId());
            Map<String, String> teacherMap = hrHessianService.queryEmployeeById(courseArrange.getTeacherId());
            request.setAttribute("teacherId", courseArrange.getTeacherId());
            request.setAttribute("teacherName", teacherMap.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/update_teachEvaluate";
    }

    /**
     * 修改教学质量评估记录
     *
     * @param teachEvaluate
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/updateTeachEvaluate")
    @ResponseBody
    public Map<String, Object> updateTeachEvaluate(HttpServletRequest request, TeachEvaluate teachEvaluate) {
        Map<String, Object> json = null;
        try {
            if (teachEvaluate.getTotal() > 100) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请正确填写分数",
                        null);
            }
            teachEvaluateBiz.update(teachEvaluate);
            json = this.resultJson(ErrorCode.SUCCESS, "提交成功",
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 一堂课的教学质量评估列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/teachEvaluateListOfOneCourseArrange")
    public String teachEvaluateListOfOneCourseArrange(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @RequestParam("courseArrangeId") Long courseArrangeId) {
        try {
            List<TeachEvaluate> teachEvaluateList = null;
            String whereSql = " status=1 and courseArrangeId=" + courseArrangeId;
            pagination.setRequest(request);
            teachEvaluateList = teachEvaluateBiz.find(pagination, whereSql);
            request.setAttribute("teachEvaluateList", teachEvaluateList);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/teachEvaluateListOfOneCourseArrange";
    }


    @RequestMapping(ADMIN_PREFIX + "/averageTeachEvaluateOfOneCourseArrange")
    public String averageTeachEvaluateOfOneCourseArrange(HttpServletRequest request, @RequestParam("courseArrangeId") Long courseArrangeId) {
        try {
            List<TeachEvaluate> teachEvaluateList = teachEvaluateBiz.find(null, " status=1 and courseArrangeId=" + courseArrangeId);
            Map<String, Object> map = new HashMap<String, Object>();
            Integer index1 = 0;
            Integer index2 = 0;
            Integer index3 = 0;
            Integer index4 = 0;
            Integer index5 = 0;
            Integer index6 = 0;
            Integer index7 = 0;
            Integer index8 = 0;
            Integer total = 0;
            Double writePercent = 0.00;
            if (teachEvaluateList != null && teachEvaluateList.size() > 0) {
                DecimalFormat df = new DecimalFormat("######0.00");
                Classes classes = classesBiz.findById(teachEvaluateList.get(0).getClassId());
                if (classes != null) {
                    map.put("writePercent", df.format(teachEvaluateList.size() * 1.00 / classes.getStudentTotalNum() * 100) + "%");
                }

                for (TeachEvaluate teachEvaluate : teachEvaluateList) {
                    index1 += teachEvaluate.getIndex1();
                    index2 += teachEvaluate.getIndex2();
                    index3 += teachEvaluate.getIndex3();
                    index4 += teachEvaluate.getIndex4();
                    index5 += teachEvaluate.getIndex5();
                    index6 += teachEvaluate.getIndex6();
                    index7 += teachEvaluate.getIndex7();
                    index8 += teachEvaluate.getIndex8();
                    total += teachEvaluate.getTotal();
                }

            }
            map.put("index1", index1 / teachEvaluateList.size());
            map.put("index2", index2 / teachEvaluateList.size());
            map.put("index3", index3 / teachEvaluateList.size());
            map.put("index4", index4 / teachEvaluateList.size());
            map.put("index5", index5 / teachEvaluateList.size());
            map.put("index6", index6 / teachEvaluateList.size());
            map.put("index7", index7 / teachEvaluateList.size());
            map.put("index8", index8 / teachEvaluateList.size());
            map.put("total", total / teachEvaluateList.size());
            request.setAttribute("map", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/averageTeachEvaluateOfOneCourseArrange";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 我的教学质量评估列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/myTeachEvaluateList")
    public String myTeachEvaluateList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            List<TeachEvaluate> teachEvaluateList = null;
            String whereSql = " status=1 and userId=" + userMap.get("linkId");
            TeachEvaluate teachEvaluate = new TeachEvaluate();
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId)) {
                whereSql += " and classId=" + classId;
                teachEvaluate.setClassId(Long.parseLong(classId));
            }
            pagination.setRequest(request);
            teachEvaluateList = teachEvaluateBiz.find(pagination, whereSql);
            request.setAttribute("teachEvaluateList", teachEvaluateList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teachEvaluate", teachEvaluate);
            request.setAttribute("className", request.getParameter("className"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teachEvaluate/my_teachEvaluate_list";
    }
}
