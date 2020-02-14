package com.keyanzizheng.controller.importexcel;


import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.keyanzizheng.biz.projecttemplate.ProjectTemplateBiz;
import com.keyanzizheng.entity.projecttemplate.ProjectTemplate;
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
@RequestMapping("/admin/ky/projectTemplateManagement")
@Controller
public class TemplateController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);
    /**
     * 当前线程的req
     */
    private final HttpServletRequest request;
    private final ProjectTemplateBiz projectTemplateBiz;

    @Autowired
    public TemplateController(HttpServletRequest request, ProjectTemplateBiz projectTemplateBiz) {
        this.request = request;
        this.projectTemplateBiz = projectTemplateBiz;
    }

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("projectTemplate")
    public void projectTemplateManagementInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("projectTemplate.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 添加课题模板
     *
     * @return 添加课题模板
     */
    @RequestMapping("save")
    public String saveProjectTemplateManagement() {
        return "/importexcel/save_template";
    }

    /**
     * 添加
     *
     * @param projectTemplate 课题模板
     */
    @RequestMapping("doSave")
    @ResponseBody
    public Map<String, Object> doSave(@ModelAttribute("projectTemplate") ProjectTemplate projectTemplate) {
        Map<String, Object> objectMap;
        try {
            projectTemplateBiz.save(projectTemplate);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("TemplateController.doSave", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 列表
     */
    @RequestMapping("list")
    public ModelAndView listProjectTemplateManagement(@ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/importexcel/import_excel_template_list");
        try {
            List<ProjectTemplate> list = projectTemplateBiz.findAll();
            mv.addObject("projectTemplates", list);
        } catch (Exception e) {
            logger.error("TemplateController.listProjectTemplateManagement", e);
        }
        return mv;
    }

    /**
     * 删除模板
     */
    @RequestMapping("del")
    @ResponseBody
    public Map<String, Object> delProjectTemplateManagement(Long id) {
        Map<String, Object> json;
        try {
            ProjectTemplate obj = projectTemplateBiz.findById(id);
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
