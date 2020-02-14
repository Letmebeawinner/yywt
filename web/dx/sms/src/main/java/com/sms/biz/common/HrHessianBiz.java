package com.sms.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.sms.entity.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 人事接口调用Biz
 *
 * @author 268
 */
@Service
public class HrHessianBiz implements Serializable {
    @Autowired
    private HrHessianService hrHessianService;

    /**
     * 获取所有教职工
     *
     * @return List<Employee>
     */
    public List<Employee> queryAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        List<Map<String, String>> maps = hrHessianService.queryAllEmployee();
        if (!CollectionUtils.isEmpty(maps)) {
            for (Map<String, String> map : maps) {
                Employee employee = new Employee(map);
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    /**
     * 获取教职工分页查询
     *
     * @return List<Map<String,String>>
     */
    public List<Employee> getEmployeeListBySql(Pagination pagination, String whereSql) {
        List<Employee> employeeList = new ArrayList<>();
        Map<String, Object> maps = hrHessianService.getEmployeeListBySql(pagination, whereSql);
        List<Map<String, String>> mapList = (List<Map<String, String>>) maps.get("employeeList");
        if (!CollectionUtils.isEmpty(mapList)) {
            for (Map<String, String> map : mapList) {
                Employee employee = new Employee(map);
                employeeList.add(employee);
            }
        }
        Map<String, String> _pagination = (Map<String, String>) maps.get("pagination");
        pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
        return employeeList;
    }

    /**
     * id查询教职工
     *
     * @return Employee
     */
    public Employee queryEmployeeById(Long id) {
        Map<String, String> map = hrHessianService.queryEmployeeById(id);
        Employee employee = new Employee();
        if (ObjectUtils.isNotNull(map)) {
            employee = new Employee(map);
        }
        return employee;
    }


    /**
     * 通过IDs，查询教职工详情
     *
     * @param employeeIds 教职工 IDs
     * @return 教职工对Map
     */
    public List<Map<String, String>> queryEmployeeByIds(String employeeIds) {
        List<Map<String, String>> employeeList = hrHessianService.queryEmployeeByIds(employeeIds);
        return employeeList;
    }


    /**
     * 获取教职工列表
     *
     * @return List<Map<String,String>>
     */
    public List<Employee> getEmployeeList(String whereSql) {
        List<Employee> employeeList = new ArrayList<>();
        Map<String, Object> maps = hrHessianService.getEmployeeListBySql(null, whereSql);
        List<Map<String, String>> mapList = (List<Map<String, String>>) maps.get("employeeList");
        if (!CollectionUtils.isEmpty(mapList)) {
            for (Map<String, String> map : mapList) {
                Employee employee = new Employee(map);
                employeeList.add(employee);
            }
        }
        return employeeList;
    }
}
