package com.renshi.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.renshi.biz.attendance.WorkStatisticsBiz;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.entity.attendance.WorkStatistics;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.employee.QueryEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 人事系统对外Hessain的Service实现类
 *
 * @author 268
 */
@Service
public class HrHessianBiz implements HrHessianService {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private WorkStatisticsBiz workStatisticsBiz;

    @Override
    public Long addEmployee(String employeeJson) {
        QueryEmployee queryEmployee = gson.fromJson(employeeJson, QueryEmployee.class);
        queryEmployee.setEmployeeType(1L);
        employeeBiz.addEmployee(queryEmployee);
        return queryEmployee.getId();
    }

    @Override
    public String queryEmployeeNameById(Long id) {
        Employee employee = employeeBiz.findById(id);
        return employee == null ? "" : employee.getName();
    }

    @Override
    public String saveEmployee(String employeeJson) {
        QueryEmployee queryEmployee = gson.fromJson(employeeJson, QueryEmployee.class);
        queryEmployee.setEmployeeType(1L);
        return employeeBiz.addEmployee(queryEmployee);
    }

    @Override
    public String updateEmployee(String employeeJson) {
        QueryEmployee queryEmployee = gson.fromJson(employeeJson, QueryEmployee.class);
        return employeeBiz.updateEmployee(queryEmployee);
    }

    @Override
    public String updateEmployeeType(String employeeJson) {
        QueryEmployee queryEmployee = gson.fromJson(employeeJson, QueryEmployee.class);
        return employeeBiz.updateEmployeeType(queryEmployee);
    }

    @Override
    public String deleteEmployee(String employeeJson) {
        QueryEmployee queryEmployee = gson.fromJson(employeeJson, QueryEmployee.class);
        return employeeBiz.deleteEmployee(queryEmployee);
    }

    @Override
    public List<Map<String, String>> queryAllEmployee() {
        List<Employee> employeeList = employeeBiz.find(null, "1=1");
        return ObjectUtils.listObjToListMap(employeeList);
    }

    @Override
    public List<Map<String, String>> queryAllTeacher() {
        List<Employee> employeeList = employeeBiz.getEmployeeListBySql(" status!=2 and employeeType=1", null);
        return ObjectUtils.listObjToListMap(employeeList);
    }

    @Override
    public Map<String, Object> getEmployeeListBySql(Pagination pagination, String whereSql) {
        whereSql += " order by sort";
        List<Employee> employeeList = employeeBiz.find(pagination, whereSql);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(employeeList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("employeeList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    @Override
    public Map<String, String> queryEmployeeById(Long id) {
        return ObjectUtils.objToMap(employeeBiz.findEmployeeById(id));
    }

    @Override
    public List<Map<String, String>> queryEmployeeByIds(String ids) {
        List<Employee> employeeList = employeeBiz.getEmployeeListBySql(" status!=2 and id in (" + ids + ")", null);
        return ObjectUtils.listObjToListMap(employeeList);
    }

    @Override
    public Map<String, String> queryEmployeeByEmployeeNo(String employeeNo) {
        return ObjectUtils.objToMap(employeeBiz.findEmployeeByEmployeeNo(employeeNo));
    }


    @Override
    public Map<Object, Long> getEmployeeCount(Integer condition) {
        Map<Object, Long> data = new HashMap<>();
        if (ObjectUtils.isNotNull(condition)) {
            String where = " status = 1";
            List<Employee> employeeList = employeeBiz.find(null, where);
            if (ObjectUtils.isNotNull(employeeList)) {
                Stream<Employee> stream = employeeList.parallelStream();
                Function<? super Employee, ?> function;
                switch (condition) {
                    case 1:
                        function = Employee::getSex;
                        break;
                    case 2:
                        function = Employee::getNationality;
                        break;
                    case 3:
                        function = Employee::getEducation;
                        break;
                    case 4:
                    default:
                        function = Employee::getEducation;
                        break;
                }
                data = stream.collect(Collectors.groupingBy(function, Collectors.counting()));
            }
        }
        return data;
    }

    @Override
    public Map<String, Object> listWorkStatistic(Pagination pagination, String year, String month, String day) {
        Map<String, Object> map = new HashMap<>();
        List<WorkStatistics> workStatisticsList = workStatisticsBiz.getWorkStatisticsList(pagination, year, month, day);
        map.put("workStatisticsList", ObjectUtils.listObjToListMap(workStatisticsList));
        map.put("pagination", pagination);
        return map;
    }

    @Override
    public Map<String, String> getEmployeeListByIds(Pagination pagination, List<Long> longs, String name) {
        Map<String, String> result = new HashMap<>(16);

        // 分页出要查询的id
        String ids = listToString(longs);
        StringBuilder whereSql = new StringBuilder("id in (").append(ids).append(")");
        // 拼装查询条件
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql.append("and name like '%").append(name).append("%'");
        }
        whereSql.append(" order by sort");
        List<Employee> employees = employeeBiz.find(pagination, whereSql.toString());

        // 根据教职工id 查询扩展
        List<Employee> queryEmployees = new ArrayList<>();
        employees.forEach(e -> queryEmployees.add(employeeBiz.findById(e.getId())));

        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        result.put("list", gson.toJson(queryEmployees));
        result.put("pagination", gson.toJson(pagination));
        return result;
    }

    /**
     * list 转 string
     *
     * @return java.util.String
     */
    public String listToString(List list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    @Override
    public String getEmployeeMobileById(Long teacherId) {
        Employee employee = employeeBiz.findById(teacherId);
        if (ObjectUtils.isNotNull(employee)) {
            return employee.getMobile() == null ? "" : employee.getMobile();
        }
        return "";
    }

    /**
     * 取消班主任
     *
     * @param employeeJson
     * @return
     */
    @Override
    public Integer cancelClassLeader(String employeeJson, int cancelType) {
        Employee employee = gson.fromJson(employeeJson, Employee.class);
        employee.setType(employeeBiz.updateType(employee.getId(), 0L, cancelType));
        return employeeBiz.update(employee);
    }

    @Override
    public String updateEmployeeTypeBySource(String employeeJson) {
        QueryEmployee queryEmployee = gson.fromJson(employeeJson, QueryEmployee.class);
        return employeeBiz.updateEmployeeTypeBySource(queryEmployee);
    }

    /**
     * 根据sysUserId查出教职工ID对应教务的classes的teacherId
     *
     * @param sysUserId
     * @return
     */
    @Override
    public Long getEmployeeBySysUserId(Long sysUserId) {
        List<Employee> employeeList = employeeBiz.find(null, " sysUserId=" + sysUserId);
        if (ObjectUtils.isNotNull(employeeList)) {
            return employeeList.get(0).getId();
        }
        return null;
    }
}
