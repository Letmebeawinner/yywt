package com.oa.controller.seal;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.seal.SealTypeBiz;
import com.oa.entity.seal.SealType;
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
 * 印章类型
 * @author lzh
 * @create 2016/12/27
 */
@Controller
@RequestMapping("/admin/oa")
public class SealTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SealTypeController.class);

    @Autowired
    private SealTypeBiz sealTypeBiz;

    private static final String sealTypeList = "/seal/seal_type_list";//印章类型
    private static final String toAddSealType = "/seal/seal_type_add";//添加印章类型
    private static final String toUpdateSealType = "/seal/seal_type_update";//修改印章类型

    @InitBinder("sealType")
    public void initBinderSealType(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("sealType.");
    }

    /**
     * @Description:印章类型列表
     * @author: lzh
     * @Param: request,pagination
     * @Return:  String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllSealType")
    public String queryAllMeeting(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("sealType") SealType sealType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(sealType);
            pagination.setRequest(request);
            List<SealType> sealTypes = sealTypeBiz.find(pagination, whereSql);
            request.setAttribute("sealTypes", sealTypes);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("SealTypeController.queryAllSealType", e);
            return this.setErrorPath(request, e);
        }
        return sealTypeList;
    }

    /**
     * @Description:添加类型
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddSealType")
    public String toAddSealType() {
        return toAddSealType;
    }

    /**
     * @Description:修改类型
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdateSealType")
    public String toUpdateSealType(HttpServletRequest request, @RequestParam("id") Long id,  @RequestParam(value = "flag", required = false) int flag) {
        try{
            SealType sealType = sealTypeBiz.findById(id);
            request.setAttribute("sealType", sealType);
            request.setAttribute("flag", flag);
        } catch(Exception e) {
            logger.error("SealTypeController.toUpdateSealType", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateSealType;
    }

    /**
     * @Description:添加保存类型
     * @author: lzh
     * @Param: [sealType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addSealType")
    @ResponseBody
    public Map<String, Object> addSealType(@ModelAttribute("sealType") SealType sealType) {
        Map<String,Object> resultMap = null;
        try {
            sealTypeBiz.save(sealType);
            resultMap=this.resultJson(ErrorCode.SUCCESS,"添加成功", null);
        } catch (Exception e) {
            logger.error("SealTypeController.addSealType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:修改类型
     * @author: lzh
     * @Param: [sealType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updateSealType")
    @ResponseBody
    public Map<String, Object> updateSealType(@ModelAttribute("sealType") SealType sealType) {
        Map<String, Object> resultMap = null;
        try {
            sealTypeBiz.update(sealType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("SealTypeController.updateSealType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除类型
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deleteSealType")
    @ResponseBody
    public Map<String, Object> delSealType(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            sealTypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("SealTypeController.delSealType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }

}
