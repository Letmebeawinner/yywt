package com.oa.controller.seal;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.seal.SealBiz;
import com.oa.biz.seal.SealFunctionBiz;
import com.oa.biz.seal.SealTypeBiz;
import com.oa.entity.seal.Seal;
import com.oa.entity.seal.SealDto;
import com.oa.entity.seal.SealFunction;
import com.oa.entity.seal.SealType;
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
 * 印章管理
 *
 * @author lzh
 * @create 2016-12-28-17:33
 */
@Controller
@RequestMapping("/admin/oa")
public class SealController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(SealController.class);

    @Autowired
    private SealBiz sealBiz;
    @Autowired
    private SealTypeBiz sealTypeBiz;
    @Autowired
    private SealFunctionBiz sealFunctionBiz;

    private static final String sealList = "/seal/seal_list";
    private static final String toAddSeal = "/seal/seal_add";
    private static final String toUpdateSeal = "/seal/seal_update";

    @InitBinder("seal")
    public void initBinderSeal(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("seal.");
    }

    /**
     * @Description:查询印章
     * @author: lzh
     * @Param: request,pagination
     * @Return:  String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllSeal")
    public String queryAllMeeting(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("seal") Seal seal) {
        try {
            pagination.setRequest(request);
            List<SealDto> sealDtos = sealBiz.getSealDtos(pagination, seal);
            List<SealType> sealTypes = sealTypeBiz.findAll();
            List<SealFunction> sealFunctions = sealFunctionBiz.findAll();
            request.setAttribute("sealDtos", sealDtos);
            request.setAttribute("sealTypes", sealTypes);
            request.setAttribute("sealFunctions", sealFunctions);
            request.setAttribute("seal", seal);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("SealController.queryAllSeal", e);
            return this.setErrorPath(request, e);
        }
        return sealList;
    }

    /**
     * @Description:添加印章
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddSeal")
    public String toAddSeal(HttpServletRequest request) {
        try{
            List<SealType> sealTypes = sealTypeBiz.findAll();
            List<SealFunction> sealFunctions = sealFunctionBiz.findAll();
            request.setAttribute("sealTypes", sealTypes);
            request.setAttribute("sealFunctions", sealFunctions);
        } catch(Exception e) {
            logger.error("SealController.toAddSeal", e);
            return this.setErrorPath(request, e);
        }
        return toAddSeal;
    }

    /**
     * @Description:去修改印章
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdateSeal")
    public String toUpdateSeal(HttpServletRequest request, @RequestParam("id") Long id, @RequestParam(value = "flag", required = false) int flag) {
        try{
            Seal seal = sealBiz.findById(id);
            List<SealType> sealTypes = sealTypeBiz.findAll();
            List<SealFunction> sealFunctions = sealFunctionBiz.findAll();
            request.setAttribute("sealTypes", sealTypes);
            request.setAttribute("sealFunctions", sealFunctions);
            request.setAttribute("seal", seal);
            request.setAttribute("flag", flag);
        } catch(Exception e) {
            logger.error("SealController.toUpdateSeal", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateSeal;
    }

    /**
     * @Description:添加保存印章
     * @author: lzh
     * @Param: [seal]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addSeal")
    @ResponseBody
    public Map<String, Object> addSaveSeal(@ModelAttribute("seal") Seal seal) {
        Map<String,Object> resultMap = null;
        try {
            sealBiz.save(seal);
            resultMap=this.resultJson(ErrorCode.SUCCESS,"添加成功", null);
        } catch (Exception e) {
            logger.error("SealController.addSeal", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:修改印章
     * @author: lzh
     * @Param: [seal]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updateSeal")
    @ResponseBody
    public Map<String, Object> updateSeal(@ModelAttribute("seal") Seal seal) {
        Map<String, Object> resultMap = null;
        try {
            sealBiz.update(seal);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("SealController.updateSeal", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除啊印章
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deleteSeal")
    @ResponseBody
    public Map<String, Object> delSeal(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            sealBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("SealController.delSeal", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }
}
