package com.base.biz.connection;

import com.a_268.base.core.BaseBiz;
import com.base.dao.connection.SysUserDepartmentDao;
import com.base.entity.connection.SysUserDepartment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户部门关联Biz
 *
 * @author s.li
 * @create 2016-12-13-16:59
 */
@Service
public class SysUserDepartmentBiz extends BaseBiz<SysUserDepartment,SysUserDepartmentDao> {
    public List<SysUserDepartment> querySysUserDepartmentByDepartmentId(Long departmentId) {
        String sql = " departmentId = " + departmentId;
        List<SysUserDepartment> departments = find(null, sql);
        return departments;
    }
}
