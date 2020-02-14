package com.base.controller.department;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.enums.StateEnum;
import com.a_268.base.util.StringUtils;
import com.base.biz.connection.DepartmentRoleBzi;
import com.base.biz.department.DepartmentBiz;
import com.base.biz.role.RoleBiz;
import com.base.biz.user.SysUserBiz;
import com.base.entity.connection.DepartmentRole;
import com.base.entity.connection.RoleResource;
import com.base.entity.department.Department;
import com.base.entity.permission.Resource;
import com.base.entity.role.Role;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门Controller
 * Created by yzl on 2016/12/6.
 */
@RequestMapping("/admin/base/department")
@Controller
public class DepartmentController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentBiz departmentBiz;

    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    private DepartmentRoleBzi departmentRoleBzi;


    @InitBinder({"department"})
    public void initDepartment(WebDataBinder binder){ binder.setFieldDefaultPrefix("department.");}

    /**
     * 查询部门列表
     * @param request HttpServletRequest
     * @return 列表页面
     */
    @RequestMapping("/queryDepartmentList")
    public String queryDepartMentList(HttpServletRequest request) {
        try {
            String whereSql = " departmentAvailable!=2 order by sort desc";
            List<Department> department = departmentBiz.find(null, whereSql);
            request.setAttribute("department", gson.toJson(department));
        } catch (Exception e) {
            logger.error("queryDepartMentList()--error", e);
        }
        return "/department/department-list";
    }

    /**
     * 通过id查询单个部门
     * @param request HttpServletRequest
     * @param id 部门id
     * @return json数据
     */
    @RequestMapping("/getDepartmentById")
    @ResponseBody
    public Map<String, Object> getDepartmentById(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            Department department = departmentBiz.findById(id);
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
     * 添加或更新部门
     *
     * @param department 部门数据对象
     * @param isMove     更新部门的类型：1 普通更新 2 成为另一个节点的子节点
     * @return json数据
     */
    @RequestMapping("/addOrUpdateDepartment")
    @ResponseBody
    public Map<String, Object> addOrUpdateDepartment(@ModelAttribute("department") Department department, @RequestParam(value = "isMove", required = false) String isMove, @RequestParam(value = "roleIds",required = false) String roleIds) {
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
                departmentBiz.save(department);
                return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, department);
            } else {
                if ("2".equals(isMove)) {
                    // department中包含两个参数 1.department.id 移动的节点的id 2.department.parentId 目标节点的id
                    Department sonDepartment = departmentBiz.findById(department.getParentId());
                    String parentIds = sonDepartment.getParentIds() + department.getParentId() + ",";
                    department.setParentIds(parentIds);
                }
                departmentBiz.update(department);
            }
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, department);
        } catch (Exception e) {
            logger.error("addOrUpdateResource()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 删除部门  假删除  改变状态
     * @param id 部门id
     * @return
     */
    @RequestMapping("/deleteDepartment")
    @ResponseBody
    public Map<String,Object> deleteDepartment(@RequestParam("id") Long id){
        Department department=departmentBiz.findById(id);
        if(department!=null){
            department.setDepartmentAvailable(2);
            departmentBiz.tx_updateDepartmentStatus(department,null);
            return this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,department);
        }else{
            return this.resultJson(ErrorCode.ERROR_SYSTEM,ErrorCode.ERROR_SYSTEM,null);
        }
    }

}
