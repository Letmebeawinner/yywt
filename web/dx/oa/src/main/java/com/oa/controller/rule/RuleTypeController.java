package com.oa.controller.rule;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.rule.RuleTypeBiz;
import com.oa.entity.rule.RuleType;
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
 * 规章类型管理
 *
 * @author ccl
 * @create 2017-01-04-17:39
 */
@Controller
@RequestMapping("/admin/oa")
public class RuleTypeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(RuleTypeController.class);
    @Autowired
    private RuleTypeBiz ruletypeBiz;

    @InitBinder("ruleType")
    public void initTelephone(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("ruleType.");
    }

    private static final String toAddRuleType = "/rule/ruleType_add";
    private static final String toUpdateRuleType = "/rule/ruleType_update";
    private static final String RuleTypeList = "/rule/ruleType_list";

    /**
     * 查询所有的类型
     *
     * @author: ccl
     * @Param: [request, pagination, ruleType]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllRuleType")
    public String queryAllRuleType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("ruleType") RuleType ruleType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(ruleType);
            whereSql+=" order by sort desc";
            pagination.setRequest(request);
            List<RuleType> ruleTypeList = ruletypeBiz.find(pagination, whereSql);
            request.setAttribute("ruleTypeList", ruleTypeList);
            request.setAttribute("ruleType",ruleType);
        } catch (Exception e) {
            logger.info("RuleTypeController--queryAllRuleType", e);
            return this.setErrorPath(request, e);
        }
        return RuleTypeList;
    }


    /**
     * @Description:去添加类型
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddRuleType")
    public String toAddRuleType(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.info("RuleTypeController--toAddRuleType", e);
            return this.setErrorPath(request, e);
        }
        return toAddRuleType;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [ruleType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveRuleType")
    @ResponseBody
    public Map<String, Object> addSaveRuleType(@ModelAttribute("ruleType") RuleType ruleType) {
        Map<String, Object> resultMap = null;
        try {
            ruletypeBiz.save(ruleType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("RuleTypeController--addSaveRuleType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toUpdateRuleType")
    public String toUpdateRuleType(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            RuleType ruleType = ruletypeBiz.findById(id);
            request.setAttribute("ruleType", ruleType);
        } catch (Exception e) {
            logger.error("RuleTypeController--toUpdateRuleType", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateRuleType;
    }


    /**
     * @Description:修改公告类型
     * @author: ccl
     * @Param: [request, ruleType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateRuleType")
    @ResponseBody
    public Map<String, Object> updateRuleType(HttpServletRequest request, @ModelAttribute("ruleType") RuleType ruleType) {
        Map<String, Object> resultMap = null;
        try {
            ruletypeBiz.update(ruleType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("RuleTypeController--updateRuleType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除公告类型
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/deleteRuleType")
    @ResponseBody
    public Map<String, Object> deleteRuleType(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            ruletypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("RuleTypeController-deleteRuleType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
