package com.jiaowu.controller.TeacherLibrary;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.teacherLibrary.TeacherLibraryBiz;
import com.jiaowu.entity.teacherLibrary.TeacherLibrary;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by MaxWe on 2017/10/18.
 */
@Controller
public class TeacherLibraryController extends BaseController {

    public static final String ADMIN_TEACHER = "/admin/jiaowu/teacher";

    private static final Logger logger = Logger.getLogger(TeacherLibraryController.class);

    @Autowired
    private TeacherLibraryBiz teacherLibraryBiz;

    @InitBinder({"teacherLibrary"})
    public void initTeacher(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("teacherLibrary.");
    }

    /**
     * 教师专题库
     *
     * @param pagination 分页
     * @return 教师专题库列表
     */
    @RequestMapping(ADMIN_TEACHER + "/teacherLibraryList")
    public ModelAndView teacherLibraryList(@ModelAttribute("pagination") Pagination pagination, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/admin/teacherLibrary/teacherLibrary_list");
        try {
            StringBuffer sb = new StringBuffer("1=1");
            String projectName = request.getParameter("projectName");
            if (!StringUtils.isTrimEmpty(projectName)) {
                sb.append(" and projectName like '%").append(projectName).append("%'");
                // 环回
                mv.addObject("projectName", projectName);
            }
            String classification = request.getParameter("classification");
            if (!StringUtils.isTrimEmpty(classification)) {
                sb.append(" and classification=" + classification);
                mv.addObject("classification", classification);
            }
            // 环回URL
            pagination.setRequest(request);
            List<TeacherLibrary> teacherLibraries = teacherLibraryBiz.find(pagination, sb.toString());
            mv.addObject("teacherLibraries", teacherLibraries);
        } catch (Exception e) {
            logger.error("TeacherLibraryController.teacherLibraryList", e);
        }
        return mv;
    }


    /**
     * 跳转到添加专题
     *
     * @return 添加专题
     */
    @RequestMapping(ADMIN_TEACHER + "/toSaveProject")
    public String toSaveProject() {
        return "/admin/teacherLibrary/create_teacherLibrary";
    }


    /**
     * 添加专题
     *
     * @param teacherLibrary 专题库
     * @return json
     */
    @RequestMapping(ADMIN_TEACHER + "/saveProject")
    @ResponseBody
    public Map<String, Object> saveProject(@ModelAttribute("teacherLibrary") TeacherLibrary teacherLibrary) {
        Map<String, Object> json;
        try {
            teacherLibraryBiz.save(teacherLibrary);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("TeacherLibraryController.saveProject", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 删除教师专题库
     *
     * @param id 自增ID
     * @return json
     */
    @RequestMapping(ADMIN_TEACHER + "/delTeacherLibrary")
    @ResponseBody
    public Map<String, Object> delTeacherLibrary(Long id) {
        Map<String, Object> json;
        try {
            teacherLibraryBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("TeacherLibraryController.delTeacherLibrary", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 跳转编辑
     *
     * @param id 自增ID
     * @return
     */
    @RequestMapping(ADMIN_TEACHER + "/toUpdateTeacherLibrary")
    public ModelAndView toUpdateTeacherLibrary(Long id) {
        ModelAndView mv = new ModelAndView("/admin/teacherLibrary/update_teacherLibrary");
        try {
            TeacherLibrary teacherLibrary = teacherLibraryBiz.findById(id);
            mv.addObject("teacherLibrary", teacherLibrary);
        } catch (Exception e) {
            logger.error("TeacherLibraryController.toUpdateTeacherLibrary", e);
        }
        return mv;
    }

    /**
     * 修改
     *
     * @param teacherLibrary 专题库
     * @return json
     */
    @RequestMapping(ADMIN_TEACHER + "/updateTeacherLibrary")
    @ResponseBody
    public Map<String, Object> updateTeacherLibrary(@ModelAttribute("teacherLibrary") TeacherLibrary teacherLibrary) {
        Map<String, Object> json;
        try {
            Integer flag = teacherLibraryBiz.update(teacherLibrary);
            if (flag > 0) {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            logger.error("TeacherLibraryController.updateTeacherLibrary", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


}
