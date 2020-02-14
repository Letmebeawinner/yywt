package com.renshi.biz.educate;

import com.a_268.base.core.BaseBiz;
import com.renshi.dao.educate.EducateEmployeeDao;
import com.renshi.entity.educate.EducateEmployee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 培训项目—员工Biz
 *
 * @author 268
 */
@Service
public class EducateEmployeeBiz extends BaseBiz<EducateEmployee, EducateEmployeeDao> {
    public List<EducateEmployee> getEducateEmployeeList(EducateEmployee EducateEmployee){
        String whereSql=" 1=1";
        whereSql+=" and status!=2 ";
        if(EducateEmployee.getEmployeeId()!=null && EducateEmployee.getEmployeeId()>0){
            whereSql+=" and employeeId="+EducateEmployee.getEmployeeId();
        }
        if(EducateEmployee.getEducateId()!=null && EducateEmployee.getEducateId()>0){
            whereSql+=" and educateId="+EducateEmployee.getEducateId();
        }
        return this.find(null,whereSql);
    }
}
