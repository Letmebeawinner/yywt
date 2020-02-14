package com.base.biz.department;

import com.a_268.base.util.StringUtils;
import com.base.biz.connection.DepartmentRoleBzi;
import com.base.entity.connection.DepartmentRole;

import org.springframework.beans.factory.annotation.Autowired;

import com.a_268.base.core.BaseBiz;
import com.base.dao.department.DepartmentDao;
import com.base.entity.department.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门Biz
 * @author s.li
 */
@Service
public class DepartmentBiz extends BaseBiz<Department, DepartmentDao>{

    @Autowired
    private DepartmentRoleBzi departmentRoleBzi;

    /**
     * 修改部门状态  该部门的子集部门状态应保持一致
     * @param department  封装部门信息对象
     */
    public void tx_updateDepartmentStatus(Department department,String roleIds){

        this.update(department);//修改该部门
        //该部门的子级部门状态应和该部门的状态保持一致
        Department _department=new Department();
        _department.setDepartmentAvailable(department.getDepartmentAvailable());
        String whereSql=" 1=1 and parentIds like '%,"+department.getId()+",%'";
        this.updateByStrWhere(_department,whereSql);
    }


}
