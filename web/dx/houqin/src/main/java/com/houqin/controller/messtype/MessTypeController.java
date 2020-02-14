package com.houqin.controller.messtype;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.messtype.MessTypeBiz;
import com.houqin.entity.messtype.MessType;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 食堂类型管理
 *
 * @author ccl
 * @create 2016-12-22-16:12
 */
@Controller
@RequestMapping("/admin/houqin")
public class MessTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MessTypeController.class);
    //添加食堂类型
    private static final String createMessype = "/messtype/add-messType";
    //修改食堂类型
    private static final String toUpdateMessType = "/messtype/update-messType";
    //食堂类型列表
    private static final String messTypeList = "/messtype/messType_list";

    @Autowired
    private MessTypeBiz messTypeBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"messType"})
    public void initMessType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("messType.");
    }


    /**
     * 去添加食堂类型
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMessType")
    public String toAddMessType(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.info("MessTypeController--toAddMessType", e);
            return this.setErrorPath(request, e);
        }
        return createMessype;
    }

    /**
     * @Description:去添加食堂类型
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/queryAllMessType")
    public String queryAllMessType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("messType") MessType messType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(messType);
            pagination.setRequest(request);
            List<MessType> messTypeList = messTypeBiz.find(pagination, whereSql);
            request.setAttribute("messTypeList", messTypeList);
            request.setAttribute("messType", messType);
        } catch (Exception e) {
            logger.info("MessTypeController--queryAllMessType", e);
            return this.setErrorPath(request, e);
        }
        return messTypeList;
    }

    /**
     * @Description:添加食堂类型
     * @author: ccl
     * @Param: [request, menuType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/addSaveMessType")
    @ResponseBody
    public Map<String, Object> addSaveMessType(HttpServletRequest request, @ModelAttribute("messType") MessType messType) {

        try {
            messTypeBiz.save(messType);
        } catch (Exception e) {
            logger.info("MessTypeController--addSaveMessType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * @Description:去修改食堂类型
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/toUpdateMessType")
    public String toUpdateMessType(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            MessType messType = messTypeBiz.findById(id);
            request.setAttribute("messType", messType);
        } catch (Exception e) {
            logger.info("MessTypeController--toUpdateMessType", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMessType;
    }


    /**
     * @Description:去修改食堂类型
     * @author: ccl
     * @Param: [request, menuType]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/updateMessType")
    @ResponseBody
    public Map<String, Object> updateMessType(HttpServletRequest request, @ModelAttribute("messType") MessType messType) {
        Map<String, Object> resultMap = null;
        try {
            messTypeBiz.update(messType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("MessTypeController--updateMessType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除食堂类型
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/delMessType")
    @ResponseBody
    public Map<String, Object> delMessType(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            messTypeBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.info("MessTypeController--delMessType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


}
