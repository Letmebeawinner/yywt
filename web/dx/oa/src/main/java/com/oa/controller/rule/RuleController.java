package com.oa.controller.rule;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.rule.RuleBiz;
import com.oa.biz.rule.RuleTypeBiz;
import com.oa.entity.rule.QueryRule;
import com.oa.entity.rule.Rule;
import com.oa.entity.rule.RuleDto;
import com.oa.entity.rule.RuleType;
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
 * 规章制度管理
 *
 * @author ccl
 * @create 2017-01-04-19:13
 */
@Controller
@RequestMapping("/admin/oa")
public class RuleController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleBiz ruleBiz;

    @Autowired
    private RuleTypeBiz ruleTypeBiz;

    @InitBinder({"rule"})
    public void initRule(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("rule.");
    }

    @InitBinder("queryRule")
    public void initRuleDto(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("queryRule.");
    }

    private static final String toAddRule = "/rule/rule_add";
    private static final String toUpdateRule = "/rule/rule_update";
    private static final String RuleList = "/rule/rule_list";

    /**
     * 查询所有的
     *
     * @author: ccl
     * @Param: [request, pagination, Rule]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllRule")
    public String queryAllRule(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("queryRule") QueryRule rule) {
        try {
            pagination.setRequest(request);
            List<RuleDto> RuleList = ruleBiz.getRulesList(pagination, rule);
            List<RuleType> ruleTypeList = ruleTypeBiz.ruleTypeList();
            request.setAttribute("ruleTypeList", ruleTypeList);
            request.setAttribute("RuleList", RuleList);
            request.setAttribute("queryRule", rule);
        } catch (Exception e) {
            logger.info("RuleController--queryAllRule", e);
            return this.setErrorPath(request, e);
        }
        return RuleList;
    }


    /**
     * @Description:去添加
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddRule")
    public String toAddRule(HttpServletRequest request) {
        try {
            List<RuleType> ruleTypeList = ruleTypeBiz.ruleTypeList();
            request.setAttribute("ruleTypeList", ruleTypeList);
        } catch (Exception e) {
            logger.info("RuleController--toAddRule", e);
            return this.setErrorPath(request, e);
        }
        return toAddRule;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [Rule]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveRule")
    @ResponseBody
    public Map<String, Object> addSaveRule(@ModelAttribute("rule") Rule rule) {
        Map<String, Object> resultMap = null;
        try {
            ruleBiz.save(rule);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("RuleController--addSaveRule", e);
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
    @RequestMapping("/toUpdateRule")
    public String toUpdateRule(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Rule rule = ruleBiz.findById(id);
            request.setAttribute("rule", rule);

            List<RuleType> ruleTypeList = ruleTypeBiz.ruleTypeList();
            request.setAttribute("ruleTypeList", ruleTypeList);
        } catch (Exception e) {
            logger.error("RuleController--toUpdateRule", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateRule;
    }


    /**
     * @Description:修改公告
     * @author: ccl
     * @Param: [request, Rule]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateRule")
    @ResponseBody
    public Map<String, Object> updateRule(HttpServletRequest request, @ModelAttribute("rule") Rule rule) {
        Map<String, Object> resultMap = null;
        try {
            ruleBiz.update(rule);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("RuleController--updateRule", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除公告
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/deleteRule")
    @ResponseBody
    public Map<String, Object> deleteRule(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            ruleBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("RuleController-deleteRule", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 是否显示在首页
     * @author: ccl
     * @Param: [request, rule]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-05
     */
    @RequestMapping("/updateShowIndex")
    @ResponseBody
    public Map<String, Object> updateShowIndex(HttpServletRequest request, @ModelAttribute("rule") Rule rule) {
        Map<String, Object> resultMap = null;
        try {
            ruleBiz.update(rule);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("RuleController--updateShowIndex", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
