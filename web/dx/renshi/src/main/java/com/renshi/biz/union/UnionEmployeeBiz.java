package com.renshi.biz.union;

import com.a_268.base.core.BaseBiz;
import com.renshi.dao.union.UnionEmployeeDao;
import com.renshi.entity.union.UnionEmployee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工会-教职工Biz
 *
 * @author 268
 */
@Service
public class UnionEmployeeBiz extends BaseBiz<UnionEmployee, UnionEmployeeDao> {

    public List<UnionEmployee> getUnionEmployeeList(UnionEmployee unionEmployee){
        String whereSql=" 1=1";
        whereSql+=" and status!=2 ";
        if(unionEmployee.getEmployeeId()!=null && unionEmployee.getEmployeeId()>0){
            whereSql+=" and employeeId="+unionEmployee.getEmployeeId();
        }
        if(unionEmployee.getUnionId()!=null && unionEmployee.getUnionId()>0){
            whereSql+=" and unionId="+unionEmployee.getUnionId();
        }
        return this.find(null,whereSql);
    }
}
