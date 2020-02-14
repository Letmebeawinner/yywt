package com.oa.controller.telephone;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.telephone.TelephoneBiz;
import com.oa.entity.telephone.Telephone;
import com.oa.utils.GenerateSqlUtil;
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
 * 电话本管理
 *
 * @author ccl
 * @create 2016-12-28-16:06
 */
@Controller
@RequestMapping("/admin/oa")
public class TelephoneController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TelephoneController.class);

    @Autowired
    private TelephoneBiz telephoneBiz;

    @InitBinder({"telephone"})
    public void initTelephone(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("telephone.");
    }

    private static final String createTelephone = "/telephone/telephone_add";
    private static final String toUpdateTelephone = "/telephone/telephone_update";
    private static final String telephoneList = "/telephone/telephone_list";

    /**
     * @Description:查询所有的电话本
     * @author: ccl
     * @Param: [request, pagination, telephone]
     * @Return: java.lang.String
     * @Date: 2016-12-28
     */
    @RequestMapping("/queryAllTelephone")
    public String queryAllTelephone(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("telephone") Telephone telephone) {
        try {
            String whereSql = GenerateSqlUtil.getSql(telephone);
            pagination.setRequest(request);
            List<Telephone> telephoneList = telephoneBiz.find(pagination, whereSql);
            request.setAttribute("telephoneList", telephoneList);
            request.setAttribute("telephone",telephone);
        } catch (Exception e) {
            logger.info("TelephoneController--queryAllTelephone", e);
            return this.setErrorPath(request, e);
        }
        return telephoneList;
    }


    /**
     * @Description:去添加描述
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-28
     */
    @RequestMapping("/toAddTelephone")
    public String toAddTelephone(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.info("TelephoneController--toAddTelephone", e);
            return this.setErrorPath(request, e);
        }
        return createTelephone;
    }


    /**
     * @Description:添加电话本
     * @author: ccl
     * @Param: [request, telephone]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/addSaveTelephone")
    @ResponseBody
    public Map<String, Object> addSaveTelephone(HttpServletRequest request, @ModelAttribute("telephone") Telephone telephone) {
        Map<String, Object> resultMap = null;
        try {
            telephoneBiz.save(telephone);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改电话
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-28
     */
    @RequestMapping("/toUpdateTelephone")
    public String toUpdateTelephone(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Telephone telephone = telephoneBiz.findById(id);
            request.setAttribute("telephone", telephone);
        } catch (Exception e) {
            logger.info("TelephoneController--toUpdateTelephone", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateTelephone;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/updateTelephone")
    @ResponseBody
    public Map<String, Object> updateTelephone(HttpServletRequest request, @ModelAttribute("telephone") Telephone telephone) {
        Map<String, Object> resultMap = null;
        try {
            telephoneBiz.update(telephone);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除电话本
     * @author: ccl
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/deleteTelephone")
    @ResponseBody
    public Map<String, Object> deleteTelephone(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            telephoneBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
