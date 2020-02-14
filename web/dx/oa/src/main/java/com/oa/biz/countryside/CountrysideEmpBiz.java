package com.oa.biz.countryside;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.oa.biz.common.HrHessianService;
import com.oa.biz.employee.EmployeeBiz;
import com.oa.dao.countryside.CountrysideEmpDao;
import com.oa.entity.countryside.CountrysideEmp;
import com.oa.entity.employee.Employee;
import com.oa.entity.sysuser.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 下乡人员Biz
 *
 * @author 268
 */
@Service
public class CountrysideEmpBiz extends BaseBiz<CountrysideEmp, CountrysideEmpDao> {

    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private HrHessianService hrHessianService;

    Gson gson = new Gson();

    public List<CountrysideEmp> getCountrysideEmpList(CountrysideEmp countrysideEmp) {
        String whereSql = " 1=1";
        whereSql += " and status!=2 ";
        if (countrysideEmp.getEmployeeId() != null && countrysideEmp.getEmployeeId() > 0) {
            whereSql += " and employeeId=" + countrysideEmp.getEmployeeId();
        }
        if (countrysideEmp.getCountrysideId() != null && countrysideEmp.getCountrysideId() > 0) {
            whereSql += " and countrysideId=" + countrysideEmp.getCountrysideId();
        }
        return this.find(null, whereSql);
    }


    public List<CountrysideEmp> queryCountryById(String whereSql, Pagination pagination) {
        List<CountrysideEmp> countrysideEmpList = this.find(pagination, whereSql);
        if (ObjectUtils.isNotNull(countrysideEmpList)) {
            for (CountrysideEmp countrysideEmp : countrysideEmpList) {
                Map<String, String> employeeMap = hrHessianService.queryEmployeeById(countrysideEmp.getEmployeeId());
                Employee employee = mapToEntity(employeeMap, Employee.class);
                if (ObjectUtils.isNotNull(employee)) {
                    countrysideEmp.setName(employee.getName());
                    countrysideEmp.setMobile(employee.getMobile());
                    countrysideEmp.setSex(Integer.parseInt(employee.getSex()));
                    countrysideEmp.setNationality(employee.getNationality());
                }
            }
        }
        return countrysideEmpList;

    }

    private <T> T mapToEntity(Map<String, String> map, Class<T> clazz) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
