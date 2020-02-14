package com.renshi.biz.institution;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.dao.institution.InstitutionDao;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.institution.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 奖惩Biz
 *
 * @author 268
 */
@Service
public class InstitutionBiz extends BaseBiz<Institution, InstitutionDao> {

    @Autowired
    private EmployeeBiz employeeBiz;

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public Institution findEmployeeById(Long id) {
        Institution institution = findById(id);
        Employee employee = employeeBiz.findById(Long.parseLong(institution.getEmployeeId()));
        if (ObjectUtils.isNotNull(employee)) {
            institution.setEmployeeName(employee.getName());
            institution.setSex(employee.getSex());
            institution.setPresentPost(employee.getPresentPost());
        }
        return institution;
    }

}
