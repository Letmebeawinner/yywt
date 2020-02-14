package com.oa.controller.union;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.oa.biz.common.HrHessianBiz;
import com.oa.biz.employee.EmployeeBiz;
import com.oa.biz.employee.FamilyBiz;
import com.oa.entity.employee.Employee;
import com.oa.entity.employee.Family;
import com.oa.entity.employee.QueryEmployee;
import com.oa.utils.AgeUtils;
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
@RequestMapping("/admin/oa")
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
    private EmployeeBiz employeeBiz;
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private FamilyBiz familyBiz;

    /**
     * 教职工人员选择列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/union/selectEmployeeList")
    public ModelAndView selectEmployeeList(HttpServletRequest request, @ModelAttribute("queryEmployee") QueryEmployee queryEmployee) {
        ModelAndView modelAndView = new ModelAndView("/union/select_employee_list");
        try {
            String whereSql = "1=1";
            if (!StringUtils.isTrimEmpty(queryEmployee.getName())) {
                whereSql += " and name like '%" + queryEmployee.getName()+"%'";
            }
            List<Employee> employeeList = hrHessianBiz.getEmployeeList( whereSql);
            modelAndView.addObject("employeeList", employeeList);
        } catch (Exception e) {
            logger.error("getEmployeeList", e);
        }
        return modelAndView;
    }

    /**
     * 人员详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEmployeeInfo")
    public ModelAndView getEmployeeById(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/union/employee_info");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Employee employee = hrHessianBiz.queryEmployeeById(id);
            if (ObjectUtils.isNotNull(employee)) {
                if(ObjectUtils.isNotNull(employee.getBirthDay())){
                    int age = AgeUtils.getAgeFromBirthTime(sdf.format(employee.getBirthDay()));
                    employee.setAge(Long.parseLong(String.valueOf(age)));
                }
            }
            modelAndView.addObject("employee", employee);

            List<Family> familyList = familyBiz.find(null, " employeeId=" + id);
            modelAndView.addObject("familyList", familyList);
        } catch (Exception e) {
            logger.error("getEmployeeInfo", e);
        }
        return modelAndView;
    }
}
