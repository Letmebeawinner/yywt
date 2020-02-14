package com.keyanzizheng.biz.employee;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.keyanzizheng.biz.common.HrHessianBiz;
import com.keyanzizheng.biz.result.TaskEmployeeBiz;
import com.keyanzizheng.common.BaseHessianService;
import com.keyanzizheng.entity.employee.Employee;
import com.keyanzizheng.entity.result.TaskEmployee;
import com.keyanzizheng.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 教职工Biz
 *
 * @author 268
 */
@Service
public class EmployeeBiz {
    @Autowired
    private TaskEmployeeBiz taskEmployeeBiz;
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    /**
     * 与课题相关教职工信息分页查询
     *
     */
    public List<Employee> getEmployeeWithTaskList(Pagination pagination, Employee employee){
        String _whereSql=" status!=2 ";
        List<TaskEmployee> taskEmployeeList=taskEmployeeBiz.find(null,_whereSql);
        List<Employee> employeeList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(taskEmployeeList)){
            String ids="";
            for(TaskEmployee taskEmployee:taskEmployeeList){
                ids+=taskEmployee.getEmployeeId().toString()+' ';
            }
            ids=ids.trim().replace(' ',',');

            String whereSql=" status!=2 ";
            if(ids!=""){
                whereSql+=" and id in ("+ids+")";
            }
            String employeeNo = employee.getEmployeeNo();
            if (!StringUtils.isTrimEmpty(employeeNo) && Integer.parseInt(employeeNo) > 0) {
                whereSql += " and employeeNo=" + employeeNo;
            }
            Long id = employee.getId();
            if (id!=null && id > 0) {
                whereSql += " and id=" + id;
            }
            String identityCard = employee.getIdentityCard();
            if (!StringUtils.isTrimEmpty(identityCard)) {
                whereSql += " and identityCard like '%" + identityCard + "%'";
            }
            String name = employee.getName();
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
            }
            whereSql += " order by id desc";
            employeeList = hrHessianBiz.getEmployeeListBySql(pagination, whereSql);
        }else{
            return null;
        }
        return employeeList;
    }
    /**
     * 教职工信息分页查询
     *
     */
    public List<Employee> getEmployeeList(Pagination pagination, Employee employee){
        List<Employee> employeeList=new ArrayList<>();
        String whereSql=" status!=2 ";
        Long taskId=employee.getResultId();
        if(taskId!=null && taskId>0){
            TaskEmployee taskEmployee=new TaskEmployee();
            taskEmployee.setTaskId(taskId);
            List<TaskEmployee> taskEmployeeList=taskEmployeeBiz.getTaskEmployeeList(taskEmployee);
            if(!CollectionUtils.isEmpty(taskEmployeeList)){
                String ids="";
                for(TaskEmployee _taskEmployee:taskEmployeeList){
                    ids+=_taskEmployee.getEmployeeId().toString()+' ';
                }
                ids=ids.trim().replace(' ',',');
                if(ids!=""){
                    whereSql+=" and id in ("+ids+")";
                }
            }else{
                return null;
            }
        }
        Long id = employee.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        String employeeNo = employee.getEmployeeNo();
        if (!StringUtils.isTrimEmpty(employeeNo) && Integer.parseInt(employeeNo) > 0) {
            whereSql += " and employeeNo=" + employeeNo;
        }
        String identityCard = employee.getIdentityCard();
        if (!StringUtils.isTrimEmpty(identityCard)) {
            whereSql += " and identityCard like '%" + identityCard + "%'";
        }
        String name = employee.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        whereSql += " order by id desc";
        employeeList = hrHessianBiz.getEmployeeListBySql(pagination, whereSql);
        return employeeList;
    }
    /**
     * 编号查询教职工
     *
     * @param employeeNo
     */
    public Employee findEmployeeByEmployeeNo(String employeeNo){
        String whereSql = " 1=1";
        whereSql += " and status!=2 and employeeNo="+employeeNo;
        List<Employee> employeeList = hrHessianBiz.getEmployeeListBySql(null, whereSql);
        if(ObjectUtils.isNull(employeeList)){
            return null;
        }
        Employee employee = hrHessianBiz.queryEmployeeById(employeeList.get(0).getId());
        return employee;
    }

    /**
     * 系统用户id查询教职工
     *
     * @param sysUserId
     */
    public Employee findEmployeeBySysUserId(Long sysUserId){
        Map<String, String> map = baseHessianService.querySysUserById(sysUserId);
        String linkId=map.get("linkId");
        if(StringUtils.isTrimEmpty(linkId)){
            return null;
        }
        return  hrHessianBiz.queryEmployeeById(Long.valueOf(linkId));
    }

    /**
     * 系统用户编号查询用户id
     *
     * @param sysUserNo
     */
    public SysUser findSysUserByNo(String sysUserNo){
        Map<String, Object> map = baseHessianService.querySysUserList(null," status!=2 and userNo="+sysUserNo);
        List<Map<String, String>> userList=(List<Map<String, String>>)map.get("userList");
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }
        return mapToEntity(userList.get(0),SysUser.class);
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
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
