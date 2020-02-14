package com.oa.controller.paper;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.paper.PaperTypeBiz;
import com.oa.entity.paper.PaperType;
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
 * 封条类型
 * @author lzh
 * @create 2016/12/27
 */
@Controller
@RequestMapping("/admin/oa")
public class PaperTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PaperTypeController.class);

    @Autowired
    private PaperTypeBiz paperTypeBiz;

    private static final String paperTypeList = "/paper/paper_type_list";
    private static final String toAddPaperType = "/paper/paper_type_add";
    private static final String toUpdatePaperType = "/paper/paper_type_update";

    @InitBinder("paperType")
    public void initBinderPaperType(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("paperType.");
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: request,pagination
     * @Return:  String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllPaperType")
    public String queryAllMeeting(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("paperType") PaperType paperType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(paperType);
            pagination.setRequest(request);
            List<PaperType> paperTypes = paperTypeBiz.find(pagination, whereSql);
            request.setAttribute("paperTypes", paperTypes);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("PaperTypeController.queryAllPaperType", e);
            return this.setErrorPath(request, e);
        }
        return paperTypeList;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddPaperType")
    public String toAddPaperType() {
        return toAddPaperType;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdatePaperType")
    public String toUpdatePaperType(HttpServletRequest request, @RequestParam("id") Long id,  @RequestParam(value = "flag", required = false) int flag) {
        try{
            PaperType paperType = paperTypeBiz.findById(id);
            request.setAttribute("paperType", paperType);
            request.setAttribute("flag", flag);
        } catch(Exception e) {
            logger.error("PaperTypeController.toUpdatePaperType", e);
        }
        return toUpdatePaperType;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [paperType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addPaperType")
    @ResponseBody
    public Map<String, Object> addPaperType(@ModelAttribute("paperType") PaperType paperType) {
        Map<String,Object> resultMap = null;
        try {
            paperTypeBiz.save(paperType);
            resultMap=this.resultJson(ErrorCode.SUCCESS,"添加成功", null);
        } catch (Exception e) {
            logger.error("PaperTypeController.addPaperType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [paperType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updatePaperType")
    @ResponseBody
    public Map<String, Object> updatePaperType(@ModelAttribute("paperType") PaperType paperType) {
        Map<String, Object> resultMap = null;
        try {
            paperTypeBiz.update(paperType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("PaperTypeController.updatePaperType", e);
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
    @RequestMapping("/deletePaperType")
    @ResponseBody
    public Map<String, Object> delPaperType(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            paperTypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("PaperTypeController.delPaperType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }

}
