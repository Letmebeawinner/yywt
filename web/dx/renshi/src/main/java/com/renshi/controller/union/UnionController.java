package com.renshi.controller.union;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.biz.union.UnionBiz;
import com.renshi.biz.union.UnionEmployeeBiz;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.employee.QueryEmployee;
import com.renshi.entity.union.EmployeeStatistics;
import com.renshi.entity.union.Union;
import com.renshi.entity.union.UnionEmployee;
import com.renshi.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class UnionController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UnionController.class);

    @InitBinder("union")
    public void initUnion(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("union.");
    }

    @InitBinder("queryEmployee")
    public void initQueryEmployee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("queryEmployee.");
    }

    @InitBinder("unionEmployee")
    public void initUnionEmployee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("unionEmployee.");
    }

    @Autowired
    private UnionBiz unionBiz;
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private UnionEmployeeBiz unionEmployeeBiz;

    /**
     * 跳转工会添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddUnion")
    public ModelAndView toAddUnion(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/union/union_add");
        try {
        } catch (Exception e) {
            logger.error("toAddUnion", e);
        }
        return modelAndView;
    }

    /**
     * 添加工会
     *
     * @param request
     * @param union
     * @return
     */
    @RequestMapping("/addUnion")
    @ResponseBody
    public Map<String, Object> addUnion(HttpServletRequest request, @ModelAttribute("union") Union union) {
        Map<String, Object> objectMap = null;
        try {
            union.setStatus(1);
            unionBiz.save(union);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", union);
        } catch (Exception e) {
            logger.error("addUnion", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除工会
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteUnion")
    @ResponseBody
    public Map<String, Object> addUnion(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            Union union = unionBiz.findById(id);
            union.setStatus(2);
            unionBiz.update(union);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", union);
        } catch (Exception e) {
            logger.error("addUnion", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 工会详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getUnionInfo")
    public ModelAndView getUnionById(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/union/union_info");
        try {
            Union union = unionBiz.findById(id);
            modelAndView.addObject("union", union);
        } catch (Exception e) {
            logger.error("getUnionInfo", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateUnion")
    public ModelAndView toUpdateUnion(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/union/union_update");
        try {
            Union union = unionBiz.findById(id);
            modelAndView.addObject("union", union);
        } catch (Exception e) {
            logger.error("toUpdateUnion", e);
        }
        return modelAndView;
    }

    /**
     * 修改工会
     *
     * @param request
     * @param union
     * @return
     */
    @RequestMapping("/updateUnion")
    @ResponseBody
    public Map<String, Object> updateUnion(HttpServletRequest request, @ModelAttribute("union") Union union) {
        Map<String, Object> objectMap = null;
        try {
            unionBiz.update(union);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", union);
        } catch (Exception e) {
            logger.error("updateUnion", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", union);
        }
        return objectMap;
    }

    /**
     * 工会列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getUnionList")
    public ModelAndView getUnionList(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination,
                                     @ModelAttribute("union") Union union) {
        ModelAndView modelAndView = new ModelAndView("/union/union_list");
        try {
            pagination.setRequest(request);
            List<Union> unionList = unionBiz.getUnionList(union, pagination);
            modelAndView.addObject("unionList", unionList);
        } catch (Exception e) {
            logger.error("getUnionList", e);
        }
        return modelAndView;
    }

    /**
     * 工会人员集合
     *
     * @param request
     * @return
     */
    @RequestMapping("/getUnionEmployeeList")
    public ModelAndView getUnionEmployeeList(HttpServletRequest request,
                                             @ModelAttribute("pagination") Pagination pagination,
                                             @ModelAttribute("unionEmployee") UnionEmployee unionEmployee) {
        ModelAndView modelAndView = new ModelAndView("/union/union_employee_list");
        try {
            String whereSql = "1=1 and unionId=" + unionEmployee.getUnionId();
            pagination.setRequest(request);
            List<UnionEmployee> unionEmployeeList = unionEmployeeBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(unionEmployeeList)) {
                for (UnionEmployee un : unionEmployeeList) {
                    Employee employee = employeeBiz.findById(un.getEmployeeId());
                    if (ObjectUtils.isNotNull(employee)) {
                        un.setEmployeeName(employee.getName());
                        un.setMobile(employee.getMobile());
                        un.setNationality(employee.getNationality());
                        un.setSex(employee.getSex());
                    }
                }
            }
            modelAndView.addObject("unionEmployeeList", unionEmployeeList);
            modelAndView.addObject("unionEmployee", unionEmployee);
        } catch (Exception e) {
            logger.error("toUpdateUnion", e);
        }
        return modelAndView;
    }

    /**
     * 教职工人员选择列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/union/selectEmployeeList")
    public ModelAndView selectEmployeeList(HttpServletRequest request,
                                           @ModelAttribute("queryEmployee") QueryEmployee queryEmployee) {
        ModelAndView modelAndView = new ModelAndView("/union/select_employee_list");
        try {
            List<Employee> employeeList = employeeBiz.getEmployeeList(queryEmployee, null);
            modelAndView.addObject("employeeList", employeeList);
        } catch (Exception e) {
            logger.error("getEmployeeList", e);
        }
        return modelAndView;
    }

    /**
     * 教职工加入工会
     *
     * @param request
     * @param unionEmployee
     * @return
     */
    @RequestMapping("/addUnionEmployee")
    @ResponseBody
    public Map<String, Object> addUnionEmployee(HttpServletRequest request,
                                                @ModelAttribute("unionEmployee") UnionEmployee unionEmployee) {
        Map<String, Object> objectMap = null;
        try {
            String employeeIds = request.getParameter("employeeIds");
            if (ObjectUtils.isNotNull(employeeIds)) {
                employeeIds = employeeIds.trim().replace(' ', ',');
                String[] _employeeIds = employeeIds.split(",");
                for (String employeeId : _employeeIds) {
                    UnionEmployee _unionEmployee = new UnionEmployee();
                    _unionEmployee.setEmployeeId(Long.valueOf(employeeId));
                    _unionEmployee.setStatus(1);
                    _unionEmployee.setUnionId(unionEmployee.getUnionId());
                    List<UnionEmployee> unionEmployeeList = unionEmployeeBiz.getUnionEmployeeList(_unionEmployee);
                    if (CollectionUtils.isEmpty(unionEmployeeList)) {
                        unionEmployeeBiz.save(_unionEmployee);
                    }
                }
            }
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("addUnionEmployee", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 修改职能
     *
     * @param request
     * @param unionEmployee
     * @return
     */
    @RequestMapping("/updateEmployeePosition")
    @ResponseBody
    public Map<String, Object> updateEmployeePosition(HttpServletRequest request,
                                                      @ModelAttribute("unionEmployee") UnionEmployee unionEmployee) {
        Map<String, Object> objectMap = null;
        try {
            List<UnionEmployee> unionEmployeeList = unionEmployeeBiz.getUnionEmployeeList(unionEmployee);
            UnionEmployee _unionEmployee = unionEmployeeList.get(0);
            _unionEmployee.setPosition(unionEmployee.getPosition());
            unionEmployeeBiz.update(_unionEmployee);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("addUnionEmployee", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 移除教职工
     *
     * @param unionEmployee
     * @return
     */
    @RequestMapping("/deleteUnionEmployee")
    @ResponseBody
    public Map<String, Object> deleteUnionEmployee(@ModelAttribute("unionEmployee") UnionEmployee unionEmployee) {
        Map<String, Object> objectMap = null;
        try {
            List<UnionEmployee> unionEmployeeList = unionEmployeeBiz.getUnionEmployeeList(unionEmployee);
            if (ObjectUtils.isNotNull(unionEmployeeList)) {
                unionEmployee = unionEmployeeList.get(0);
                unionEmployeeBiz.deleteById(unionEmployee.getId());
                objectMap = this.resultJson(ErrorCode.SUCCESS, "移除成功", unionEmployee);
            }
        } catch (Exception e) {
            logger.error("updateUnion", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", unionEmployee);
        }
        return objectMap;
    }

    /**
     * 工会人员统计
     *
     * @param request
     * @return
     */
    @RequestMapping("/employeeStatistics")
    public ModelAndView employeeStatistics(HttpServletRequest request,
                                           @ModelAttribute("pagination") Pagination pagination,
                                           @ModelAttribute("union") Union union) {
        ModelAndView modelAndView = new ModelAndView("/union/employeeStatistics");
        try {
            pagination.setRequest(request);
            List<Union> unionList = unionBiz.getUnionList(union, pagination);
            List<EmployeeStatistics> employeeStatisticsList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(unionList)) {
                for (Union _union : unionList) {
                    EmployeeStatistics employeeStatistics = new EmployeeStatistics();
                    employeeStatistics.setUnionName(_union.getName());
                    QueryEmployee queryEmployee = new QueryEmployee();
                    queryEmployee.setUnionId(_union.getId());
                    List<Employee> employeeList = employeeBiz.getEmployeeList(queryEmployee, null);
                    if (!CollectionUtils.isEmpty(employeeList)) {
                        employeeStatistics.setEmpNumber(Long.valueOf(employeeList.size()));
                        Long manNumber = 0L;
                        Long sumAge = 0L;
                        for (Employee _queryEmployee : employeeList) {
                            if (_queryEmployee.getSex() == 1) {
                                manNumber += 1;
                            }
                            sumAge += _queryEmployee.getAge();
                        }
                        employeeStatistics.setAgeAvg(sumAge / employeeStatistics.getEmpNumber());
                        employeeStatistics.setManNumber(manNumber);
                        employeeStatistics.setWomanNumber(employeeStatistics.getEmpNumber() - manNumber);
                    } else {
                        employeeStatistics.setEmpNumber(0L);
                        employeeStatistics.setAgeAvg(0L);
                        employeeStatistics.setManNumber(0L);
                        employeeStatistics.setWomanNumber(0L);
                    }
                    employeeStatisticsList.add(employeeStatistics);
                }
            }
            modelAndView.addObject("employeeStatisticsList", employeeStatisticsList);
        } catch (Exception e) {
            logger.error("employeeStatistics", e);
        }
        return modelAndView;
    }
}
