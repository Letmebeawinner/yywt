package com.renshi.controller.department;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.common.BaseHessianBiz;
import com.renshi.entity.department.DepartMent;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.employee.QueryEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class DepartMentController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DepartMentController.class);

    @InitBinder("department")
    public void initDepartMent(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("department.");
    }

    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 查询部门列表
     *
     * @return
     */
    @RequestMapping("/departmentList")
    public ModelAndView departmentList(@ModelAttribute("departMent") DepartMent departMent) {
        ModelAndView modelAndView = new ModelAndView("/department/department-list");
        try {
            List<DepartMent> department = baseHessianBiz.getDepartMentList(departMent);
            modelAndView.addObject("department", gson.toJson(department));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 通过id查询单个部门
     *
     * @param request HttpServletRequest
     * @param id      部门id
     * @return json数据
     */
    @RequestMapping("/getDepartmentById")
    @ResponseBody
    public Map<String, Object> getDepartmentById(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            DepartMent department = baseHessianBiz.queryDepartemntById(id);
            if (department == null) {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("department", department);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, map);
        } catch (Exception e) {
            logger.error("getDepartmentById()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * 删除部门  假删除  改变状态
     *
     * @param id 部门id
     * @return
     */
    @RequestMapping("/deleteDepartment")
    @ResponseBody
    public Map<String, Object> deleteDepartment(@RequestParam("id") Long id) {
        try {
            baseHessianBiz.deleteDepartment(id);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.ERROR_SYSTEM, null);

    }

    /**
     * 添加或更新部门
     *
     * @param department 部门数据对象
     * @param isMove     更新部门的类型：1 普通更新 2 成为另一个节点的子节点
     * @return json数据
     */
    @RequestMapping("/addOrUpdateDepartment")
    @ResponseBody
    public Map<String, Object> addOrUpdateDepartment(@ModelAttribute("department") DepartMent department, @RequestParam(value = "isMove", required = false) String isMove, @RequestParam(value = "roleIds",required = false) String roleIds) {
        try {
            if (!"2".equals(isMove)) {
                if (StringUtils.isTrimEmpty(department.getDepartmentName())) {
                    return resultJson(ErrorCode.ERROR_PARAMETER, "部门名称不能为空", department);
                }
                if (StringUtils.isTrimEmpty(department.getDepartmentDesc())) {
                    return resultJson(ErrorCode.ERROR_PARAMETER, "部门描述不能为空", department);
                }
            }
            if (department.getId() == null || department.getId() == 0) {
                department.setParentId(0L);
                department.setParentIds("0,");
                baseHessianBiz.addDepartment(department);
                return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, department);
            } else {
                if ("2".equals(isMove)) {
                    // department中包含两个参数 1.department.id 移动的节点的id 2.department.parentId 目标节点的id
                    DepartMent sonDepartment = baseHessianBiz.queryDepartemntById(department.getParentId());
                    String parentIds = sonDepartment.getParentIds() + department.getParentId() + ",";
                    department.setParentIds(parentIds);
                }
                baseHessianBiz.updateDepartment(department);
            }
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, department);
        } catch (Exception e) {
            logger.error("addOrUpdateResource()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 部门选择列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/selectDepartMentList")
    public ModelAndView selectEmployeeList(HttpServletRequest request,
                                           @ModelAttribute("departMent") DepartMent departMent) {
        ModelAndView modelAndView = new ModelAndView("/department/select_department_list");
        try {
            List<DepartMent> departMentList = baseHessianBiz.getDepartMentList(departMent);
            modelAndView.addObject("departMentList", departMentList);
        } catch (Exception e) {
            logger.error("getEmployeeList", e);
        }
        return modelAndView;
    }
}
