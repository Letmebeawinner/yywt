package com.jiaowu.controller.studyTest;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.studyTest.StudyTestBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.studyTest.StudyTest;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/8/23.
 */
@Controller
public class StudyTestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(StudyTestController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @InitBinder("studyTest")
    public void initBinderStudyTest(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("studyTest.");
    }

    private static final String ADMIN_PREFIX = "/admin/jiaowu/studyTest";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/studyTest";
    @Autowired
    private StudyTestBiz studyTestBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private BaseHessianService baseHessianService;

    /**
     * 跳转到创建学习考试成绩的页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateStudyTest")
    public String toCreateStudyTest(HttpServletRequest request) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            Integer integer = baseHessianService.getSysUserDepartment(userId);
            request.setAttribute("type", integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/studyTest/create_studyTest";
    }

    /**
     * 创建学习考试成绩
     *
     * @param studyTest
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/createStudyTest")
    @ResponseBody
    public Map<String, Object> createStudyTest(@ModelAttribute("studyTest") StudyTest studyTest) {
        Map<String, Object> json = null;
        try {
            if (studyTest.getUserId() == null || studyTest.getUserId().equals(0L)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "学员不能为空", null);
            }
            String errorInfo = validateStudyTest(studyTest);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
            List<User> userList = userBiz.find(null, "id=" + studyTest.getUserId());
            List<Classes> classesList = classesBiz.find(null, " id=" + userList.get(0).getClassId());
            if (classesList == null || classesList.size() == 0) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "该学员所在班次已不存在", null);
            }
            studyTest.setClassId(classesList.get(0).getId());
            studyTest.setClassTypeId(classesList.get(0).getClassTypeId());
//            if (ObjectUtils.isNotNull(studyTest.getOnlineStudy())) {
//                studyTest.setTotal(studyTest.getOnlineStudy() * 0.2 + studyTest.getSearchReport() * 0.3 + studyTest.getGraduateTest() * 0.5);
//            } else {
                studyTest.setTotal(0.0);
//            }

            studyTestBiz.save(studyTest);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
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
     * @Description 学习考试成绩列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/studyTestList")
    public String studyTestList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {

            List<StudyTest> studyTestList = null;
            String whereSql = " status=1";
            StudyTest studyTest = new StudyTest();
            String name = request.getParameter("name");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
                studyTest.setName(name);
            }

            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            List<Classes> classesList = null;
            if (userMap.get("userType").equals("2")) {
                classesList = classesBiz.find(null, " status=1 and teacherId=" + userMap.get("linkId"));
                if (classesList != null && classesList.size() > 0) {
                    whereSql += " and classId=" + classesList.get(0).getId();

                }
            }else if(userMap.get("userType").equals("3")){
                whereSql += " and userId=" +userMap.get("linkId");
                studyTestList = studyTestBiz.find(pagination, whereSql);
                request.setAttribute("isStudent",true);
            }

            pagination.setRequest(request);
            if (classesList != null && classesList.size() > 0) {
                studyTestList = studyTestBiz.find(pagination, whereSql);
            }
            request.setAttribute("studyTestList", studyTestList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("studyTest", studyTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/studyTest/studyTest_list";
    }


    /**
     * 跳转到修改学习考试成绩的页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateStudyTest")
    public String toUpdateStudyTest(HttpServletRequest request, Long id) {
        try {
            List<StudyTest> studyTestList = studyTestBiz.find(null, " id=" + id);
            request.setAttribute("studyTest", studyTestList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/studyTest/update_studyTest";
    }

    /**
     * 修改学习考试成绩
     *
     * @param studyTest
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/updateStudyTest")
    @ResponseBody
    public Map<String, Object> updateStudyTest(@ModelAttribute("studyTest") StudyTest studyTest) {
        Map<String, Object> json = null;
        try {
            String errorInfo = validateStudyTest(studyTest);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
//            studyTest.setTotal(studyTest.getOnlineStudy() * 0.2 + studyTest.getSearchReport() * 0.3 + studyTest.getGraduateTest() * 0.5);
            studyTest.setTotal(0.0);
            studyTestBiz.update(studyTest);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 删除学习考试成绩
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/delStudyTest")
    @ResponseBody
    public Map<String, Object> delStudyTest(Long id) {
        Map<String, Object> json = null;
        try {
            studyTestBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证学习考试成绩
     *
     * @param studyTest
     * @return
     */
    public String validateStudyTest(StudyTest studyTest) {
        if (StringUtils.isTrimEmpty(studyTest.getGraduateNumber())) {
            return "毕（结）业证号不能为空";
        }
        if (!com.jiaowu.common.StringUtils.isNumeric(studyTest.getGraduateNumber())) {
            return "请正确填写毕（结）业证号";
        }
//        if (studyTest.getOnlineStudy() == null) {
//            return "在线学习不能为空";
//        }
//        if (studyTest.getSearchReport() == null) {
//            return "调研报告不能为空";
//        }
//        if (studyTest.getGraduateTest() == null) {
//            return "毕业考试不能为空";
//        }
        return null;
    }

    /**
     * 去批量导入
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toAddBatchStudyTest")
    public String toAddBatchSysUser(HttpServletRequest request) {
        return "/admin/studyTest/import_user_study_test";
    }

    /**
     * 批量导入学习成绩
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/batchStudyTest")
    public String batchStudyTest(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            String errorInfo = studyTestBiz.batchImportStudyTest(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            request.setAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/studyTest/import_user_study_test";
    }
}
