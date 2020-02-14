package com.renshi.biz.countryside;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.dao.countryside.CountrysideEmpDao;
import com.renshi.entity.countryside.CountrysideEmp;
import com.renshi.entity.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 下乡人员Biz
 *
 * @author 268
 */
@Service
public class CountrysideEmpBiz extends BaseBiz<CountrysideEmp, CountrysideEmpDao> {

    @Autowired
    private EmployeeBiz employeeBiz;

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
                Employee employee = employeeBiz.findById(countrysideEmp.getEmployeeId());
                if (ObjectUtils.isNotNull(employee)) {
                    countrysideEmp.setName(employee.getName());
                    countrysideEmp.setMobile(employee.getMobile());
                    countrysideEmp.setSex(employee.getSex());
                    countrysideEmp.setNationality(employee.getNationality());
                }
            }
        }
        return countrysideEmpList;

    }


}
