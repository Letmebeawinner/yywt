package com.oa.controller.paper;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.paper.PaperFunctionBiz;
import com.oa.entity.paper.PaperFunction;
import com.oa.utils.GenerateSqlUtil;
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
 * 封条用途
 * @author lzh
 * @create 2016/12/27
 */
@Controller
@RequestMapping("/admin/oa")
public class PaperFunctionController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PaperFunctionController.class);

    @Autowired
    private PaperFunctionBiz paperFunctionBiz;

    private static final String paperFunctionList = "/paper/paper_function_list";
    private static final String toAddPaperFunction = "/paper/paper_function_add";
    private static final String toUpdatePaperFunction = "/paper/paper_function_update";

    @InitBinder("paperFunction")
    public void initBinderPaperFunction(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("paperFunction.");
    }

    /**
     * @Description:查询所有封条用途
     * @author: lzh
     * @Param: request,pagination
     * @Return:  String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllPaperFunction")
    public String queryAllMeeting(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("paperFunction") PaperFunction paperFunction) {
        try {
            String whereSql = GenerateSqlUtil.getSql(paperFunction);
            pagination.setRequest(request);
            List<PaperFunction> paperFunctions = paperFunctionBiz.find(pagination, whereSql);
            request.setAttribute("paperFunctions", paperFunctions);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("PaperFunctionController.queryAllPaperFunction", e);
            return this.setErrorPath(request, e);
        }
        return paperFunctionList;
    }

    /**
     * @Description:跳到增加粉条用途
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddPaperFunction")
    public String toAddPaperFunction() {
        return toAddPaperFunction;
    }

    /**
     * @Description:更新封条用途
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdatePaperFunction")
    public String toUpdatePaperFunction(HttpServletRequest request, @RequestParam("id") Long id,  @RequestParam(value = "flag", required = false) int flag) {
        try{
            PaperFunction paperFunction = paperFunctionBiz.findById(id);
            request.setAttribute("paperFunction", paperFunction);
            request.setAttribute("flag", flag);
        } catch(Exception e) {
            logger.error("PaperFunctionController.toUpdatePaperFunction", e);
        }
        return toUpdatePaperFunction;
    }

    /**
     * @Description:增加封条用途
     * @author: lzh
     * @Param: [paperFunction]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addPaperFunction")
    @ResponseBody
    public Map<String, Object> addPaperFunction(@ModelAttribute("paperFunction") PaperFunction paperFunction) {
        Map<String,Object> resultMap = null;
        try {
            paperFunctionBiz.save(paperFunction);
            resultMap=this.resultJson(ErrorCode.SUCCESS,"添加成功", null);
        } catch (Exception e) {
            logger.error("PaperFunctionController.addPaperFunction", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:更新封条用途
     * @author: lzh
     * @Param: [paperFunction]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updatePaperFunction")
    @ResponseBody
    public Map<String, Object> updatePaperFunction(@ModelAttribute("paperFunction") PaperFunction paperFunction) {
        Map<String, Object> resultMap = null;
        try {
            paperFunctionBiz.update(paperFunction);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("PaperFunctionController.updatePaperFunction", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除封条用途
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deletePaperFunction")
    @ResponseBody
    public Map<String, Object> delPaperFunction(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            paperFunctionBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("PaperFunctionController.delPaperFunction", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }

}
