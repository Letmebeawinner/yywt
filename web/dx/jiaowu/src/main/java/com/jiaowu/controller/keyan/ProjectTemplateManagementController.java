package com.jiaowu.controller.keyan;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.BaseHessianBiz;
import com.jiaowu.biz.projecttemplate.ProjectTemplateBiz;
import com.jiaowu.entity.projecttemplate.ProjectTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 课题模版管理
 *
 * @author YaoZhen
 * @date 11-14, 18:39, 2017.
 */
@RequestMapping("/admin/jiaowu/ky/projectTemplateManagement")
@Controller
public class ProjectTemplateManagementController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectTemplateManagementController.class);
    /**
     * 当前线程的req
     */
    @Autowired private HttpServletRequest request;
    @Autowired private ProjectTemplateBiz projectTemplateBiz;
    @Autowired private BaseHessianBiz baseHessianBiz;
    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder({"projectTemplate"})
    public void projectTemplateManagementInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("projectTemplate.");
    }

    /**
     * 添加课题模板
     *
     * @return 添加课题模板
     */
    @RequestMapping("save")
    public String saveProjectTemplateManagement() {
        return "/admin/projectTemplate/save_project_template";
    }

    /**
     * 添加
     *
     * @param projectTemplate 课题模板
     * @return
     */
    @RequestMapping("doSave")
    @ResponseBody
    public Map<String, Object> doSave(@ModelAttribute("projectTemplate") ProjectTemplate projectTemplate) {
        Map<String, Object> objectMap;
        try {
            projectTemplateBiz.save(projectTemplate);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("ProjectTemplateManagementController.doSave", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 列表
     *
     * @param pagination
     * @return
     */
    @RequestMapping("list")
    public ModelAndView listProjectTemplateManagement(@ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/admin/projectTemplate/list_project_template");
        try {
            List<ProjectTemplate> list = projectTemplateBiz.findAll();
            mv.addObject("projectTemplates", list);

            // 如果当前用户不是管理员和部门领导, 不具有下载模板和添加模板权限
            Long isKeYan =  baseHessianBiz.queryUserRoleByUserId(SysUserUtils.getLoginSysUserId(request));
            if (isKeYan != 0L) {
                mv.addObject("isKeYan", true);
            }

        } catch (Exception e) {
            logger.error("ProjectTemplateManagementController.listProjectTemplateManagement", e);
        }
        return mv;
    }

    /**
     * 删除模板
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    @ResponseBody
    public Map<String, Object> delProjectTemplateManagement(Long id) {
        Map<String, Object> json;
        try {
            ProjectTemplate obj = projectTemplateBiz.findById(id);
            // 任何时候都应当先查询再删除
            if (ObjectUtils.isNotNull(obj)) {
                projectTemplateBiz.deleteById(id);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ConferenceController.delMeetingTopic", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
}
