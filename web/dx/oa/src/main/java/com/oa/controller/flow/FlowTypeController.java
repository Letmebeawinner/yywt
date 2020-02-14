package com.oa.controller.flow;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.flow.FlowTypeBiz;
import com.oa.entity.flow.FlowType;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 流程分类
 *
 * @author ccl
 * @create 2017-01-05-17:54
 */
@Controller
@RequestMapping("/admin/oa/")
public class FlowTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FlowTypeController.class);
    @Autowired
    private FlowTypeBiz flowTypeBiz;

    @InitBinder({"flowType"})
    public void initFlowType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("flowType.");
    }

    private static final String toAddFlowType = "/flow/flowType_add";
    private static final String toUpdateFlowType = "/flow/flowType_update";
    private static final String flowTypeList = "/flow/flowType_list";

    /**
     * 查询所有的类型
     *
     * @author: ccl
     * @Param: [request, pagination, FlowType]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllFlowType")
    public String queryAllFlowType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("flowType") FlowType flowType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(flowType);
            whereSql += " order by sort desc";
            pagination.setRequest(request);
            List<FlowType> flowTypeList = flowTypeBiz.find(pagination, whereSql);
            request.setAttribute("flowTypeList", gson.toJson(flowTypeList));
            request.setAttribute("flowType", flowType);
        } catch (Exception e) {
            logger.info("FlowTypeController--queryAllFlowType", e);
            return this.setErrorPath(request, e);
        }
        return flowTypeList;
    }


    /**
     * @Description:去添加类型
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddFlowType")
    public String toAddFlowType(HttpServletRequest request) {
        try {
            List<FlowType> flowTypeList = flowTypeBiz.flowTypeList();
            request.setAttribute("flowTypeList", flowTypeList);
        } catch (Exception e) {
            logger.info("FlowTypeController--toAddFlowType", e);
            return this.setErrorPath(request, e);
        }
        return toAddFlowType;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [FlowType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveFlowType")
    @ResponseBody
    public Map<String, Object> addSaveFlowType(@ModelAttribute("flowType") FlowType flowType) {
        Map<String, Object> resultMap = null;
        try {
            flowTypeBiz.save(flowType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("FlowTypeController--addSaveFlowType", e);
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
    @RequestMapping("/toUpdateFlowType")
    public String toUpdateFlowType(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            FlowType flowType = flowTypeBiz.findById(id);
            request.setAttribute("flowType", flowType);

            List<FlowType> flowTypeList = flowTypeBiz.flowTypeList();
            request.setAttribute("flowTypeList", flowTypeList);
        } catch (Exception e) {
            logger.error("FlowTypeController--toUpdateFlowType", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateFlowType;
    }


    /**
     * @Description:修改公告类型
     * @author: ccl
     * @Param: [request, FlowType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateFlowType")
    @ResponseBody
    public Map<String, Object> updateFlowType(HttpServletRequest request, @ModelAttribute("FlowType") FlowType FlowType) {
        Map<String, Object> resultMap = null;
        try {
            flowTypeBiz.update(FlowType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("FlowTypeController--updateFlowType", e);
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
    @RequestMapping("/deleteFlowType")
    public String deleteFlowType(HttpServletRequest request, @RequestParam("ids") String ids) {
        try {
            String [] id=ids.split(",");
            List<Long>  longList= new ArrayList<>();
            for (int i=0;i<id.length;i++){
                longList.add(Long.parseLong(id[i]));
            }
            flowTypeBiz.deleteByIds(longList);
        } catch (Exception e) {
            logger.error("FlowTypeController-deleteFlowType", e);
        }
        return "redirect:/admin/oa/queryAllFlowType.json";
    }


}
