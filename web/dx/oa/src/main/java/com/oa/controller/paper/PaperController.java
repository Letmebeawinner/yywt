package com.oa.controller.paper;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.paper.PaperBiz;
import com.oa.biz.paper.PaperFunctionBiz;
import com.oa.biz.paper.PaperTypeBiz;
import com.oa.entity.department.DepartMent;
import com.oa.entity.paper.Paper;
import com.oa.entity.paper.PaperDto;
import com.oa.entity.paper.PaperFunction;
import com.oa.entity.paper.PaperType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 封条控制层
 *
 * @author lzh
 * @create 2016-12-28-17:33
 */
@Controller
@RequestMapping("/admin/oa")
public class PaperController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperBiz paperBiz;
    @Autowired
    private PaperFunctionBiz paperFunctionBiz;
    @Autowired
    private PaperTypeBiz paperTypeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    private static final String paperList = "/paper/paper_list";
    private static final String toAddPaper = "/paper/paper_add";
    private static final String toUpdatePaper = "/paper/paper_update";

    @InitBinder("paper")
    public void initBinderPaper(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("paper.");
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: request,pagination
     * @Return:  String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllPaper")
    public String queryAllMeeting(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("paper") Paper paper) {
        try {
            pagination.setRequest(request);
            List<PaperDto> paperDtos = paperBiz.getPaperDtos(pagination, paper);
            List<PaperType> paperTypes = paperTypeBiz.findAll();
            List<PaperFunction> paperFunctions = paperFunctionBiz.findAll();
            request.setAttribute("paperDtos", paperDtos);
            request.setAttribute("paperTypes", paperTypes);
            request.setAttribute("paperFunctions", paperFunctions);
            request.setAttribute("paper", paper);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("PaperController.queryAllPaper", e);
            return this.setErrorPath(request, e);
        }
        return paperList;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddPaper")
    public String toAddPaper(HttpServletRequest request) {
        try{
            List<PaperType> paperTypes = paperTypeBiz.findAll();
            List<PaperFunction> paperFunctions = paperFunctionBiz.findAll();
            DepartMent departMent = new DepartMent();
            List<DepartMent> departments = baseHessianBiz.getDepartMentList(departMent);
            request.setAttribute("paperTypes", paperTypes);
            request.setAttribute("departments", gson.toJson(departments));
            request.setAttribute("paperFunctions", paperFunctions);
        } catch(Exception e) {
            logger.error("PaperController.toAddPaper", e);
            return this.setErrorPath(request, e);
        }
        return toAddPaper;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdatePaper")
    public String toUpdatePaper(HttpServletRequest request, @RequestParam("id") Long id, @RequestParam(value = "flag", required = false) int flag) {
        try{
            Paper paper = paperBiz.findById(id);
            List<PaperType> paperTypes = paperTypeBiz.findAll();
            List<PaperFunction> paperFunctions = paperFunctionBiz.findAll();
            DepartMent _department = new DepartMent();
            List<DepartMent> departments = baseHessianBiz.getDepartMentList(_department);
            DepartMent departMent = baseHessianBiz.queryDepartemntById(paper.getDepartmentId());
            request.setAttribute("paperTypes", paperTypes);
            request.setAttribute("paperFunctions", paperFunctions);
            if (departMent != null) {
                request.setAttribute("departmentName", departMent.getDepartmentName());
            }
            request.setAttribute("departments", gson.toJson(departments));
            request.setAttribute("paper", paper);
            request.setAttribute("flag", flag);
        } catch(Exception e) {
            logger.error("PaperController.toUpdatePaper", e);
            return this.setErrorPath(request, e);
        }
        return toUpdatePaper;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [paper]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addPaper")
    @ResponseBody
    public Map<String, Object> addPaper(@ModelAttribute("paper") Paper paper) {
        Map<String,Object> resultMap = null;
        try {
            paperBiz.save(paper);
            resultMap=this.resultJson(ErrorCode.SUCCESS,"添加成功", null);
        } catch (Exception e) {
            logger.error("PaperController.addPaper", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [paper]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updatePaper")
    @ResponseBody
    public Map<String, Object> updatePaper(@ModelAttribute("paper") Paper paper) {
        Map<String, Object> resultMap = null;
        try {
            paperBiz.update(paper);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("PaperController.updatePaper", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deletePaper")
    @ResponseBody
    public Map<String, Object> delPaper(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            paperBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("PaperController.delPaper", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }
}
