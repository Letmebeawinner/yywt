package com.keyanzizheng.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keyanzizheng.common.HrHessianService;
import com.keyanzizheng.entity.employee.Employee;
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
     * @return List<Employee>
     */
   public  List<Employee> queryAllEmployee(){
        List<Employee> employeeList=new ArrayList<>();
       List<Map<String,String>> maps=hrHessianService.queryAllEmployee();
        if(!CollectionUtils.isEmpty(maps)){
            for(Map<String,String> map:maps){
                Employee employee=mapToEntity(map,Employee.class);
                employeeList.add(employee);
            }
        }
        return employeeList;
    }
    /**
     * 获取教职工分页查询
     * @return List<Map<String,String>>
     */
    public List<Employee> getEmployeeListBySql(Pagination pagination, String whereSql){
        List<Employee> employeeList=new ArrayList<>();
        Map<String,Object> maps=hrHessianService.getEmployeeListBySql(pagination,whereSql);
        List<Map<String,String>> mapList=(List<Map<String,String>>)maps.get("employeeList");
        if(!CollectionUtils.isEmpty(mapList)){
            for(Map<String,String> map:mapList){
                Employee employee=mapToEntity(map,Employee.class);
                employeeList.add(employee);
            }
        }
        Map<String,String> _pagination=(Map<String,String>)maps.get("pagination");
        if(ObjectUtils.isNotNull(_pagination)){
            pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
            pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));
        }
        return employeeList;
    }
    /**
     * id查询教职工
     * @return Employee
     */
    public  Employee queryEmployeeById(Long id){
        Map<String,String> map=hrHessianService.queryEmployeeById(id);
        Employee employee=new Employee();
        if(ObjectUtils.isNotNull(map)){
            employee=mapToEntity(map,Employee.class);
        }else{
            return null;
        }
        return employee;
    }
    /**
     * map集合转实体类
     *
     * @param map   map集合
     * @param clazz 实体类类对象
     * @param <T>   实体类
     * @return 实体类对象
     */
    private <T> T mapToEntity(Map<String, String> map, Class<T> clazz) {
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
