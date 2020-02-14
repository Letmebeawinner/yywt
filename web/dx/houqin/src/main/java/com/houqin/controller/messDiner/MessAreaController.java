package com.houqin.controller.messDiner;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.mess.MessBiz;
import com.houqin.biz.messArea.MessAreaBiz;
import com.houqin.controller.mess.MessController;
import com.houqin.entity.mess.Mess;
import com.houqin.entity.messArea.MessArea;
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
 * 食堂区域管理
 *
 * @author YaoZhen
 * @create 06-19, 13:45, 2017.
 */
@Controller
@RequestMapping("/admin/houqin")
public class MessAreaController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MessController.class);
    //添加食堂区域
    private static final String createMessArea = "/mess/add-messArea";
    //修改食堂区域
    private static final String toUpdateMessArea = "/mess/update-messArea";
    //食堂管理区域
    private static final String messAreaList = "/mess/messArea_list";


    @Autowired
    private MessAreaBiz messAreaBiz;

    @Autowired
    private MessBiz messBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"messArea"})
    public void initMessArea(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("messArea.");
    }


    /**
     * @Description:去添加食堂
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/queryAllMessArea")
    public String queryAllMessArea(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("messArea") MessArea messArea) {
        try {
            String whereSql = GenerateSqlUtil.getSql(messArea);
            pagination.setRequest(request);
            List<MessArea> messAreaList = messAreaBiz.find(pagination, whereSql);

            List<Mess> messList = messBiz.find(null, " 1=1");
            request.setAttribute("messList", messList);

            request.setAttribute("messAreaList", messAreaList);
            request.setAttribute("messArea", messArea);
        } catch (Exception e) {
            logger.error("MessController--queryAllMess", e);
            return this.setErrorPath(request, e);
        }
        return messAreaList;
    }


    /**
     * 去添加食堂区域
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMessArea")
    public String toAddMessArea(HttpServletRequest request) {
        try {
            List<Mess> messList = messBiz.find(null, " 1=1");
            request.setAttribute("messList", messList);
        } catch (Exception e) {
            logger.error("MessAreaController--toAddMessArea", e);
            return this.setErrorPath(request, e);
        }
        return createMessArea;
    }


    /**
     * @Description:添加食堂区域
     * @author: ccl
     * @Param: [request, menu]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/addMessArea")
    @ResponseBody
    public Map<String, Object> addMessArea(HttpServletRequest request, @ModelAttribute("messArea") MessArea messArea) {
        Map<String, Object> resultMap = null;
        try {
            messAreaBiz.save(messArea);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("MessController--addMessArea", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:去修改食堂
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/toUpdateMessArea")
    public String toUpdateMessArea(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            List<Mess> messList = messBiz.find(null, " 1=1");
            request.setAttribute("messList", messList);

            MessArea messArea = messAreaBiz.findById(id);
            request.setAttribute("messArea", messArea);
        } catch (Exception e) {
            logger.error("MessController--toUpdateMess", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMessArea;
    }


    /**
     * @Description:去修改食堂
     * @author: ccl
     * @Param: [request, menu]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/updateMessArea")
    @ResponseBody
    public Map<String, Object> updateMessArea(HttpServletRequest request, @ModelAttribute("messArea") MessArea messArea) {
        Map<String, Object> resultMap = null;
        try {
            messAreaBiz.update(messArea);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("MessController--updateMessArea", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除食堂
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/delMessArea")
    @ResponseBody
    public Map<String, Object> delMessArea(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            messAreaBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("MessController--delMessArea", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
