package com.keyanzizheng.entity.result;

import com.keyanzizheng.entity.approvebill.ApproveBill;
import com.keyanzizheng.entity.employee.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 成果
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryResult extends Result {
    private static final long serialVersionUID = -3545341344340282402L;
    private ApproveBill approveBill;//成果审批记录
    private String employeeName; //申报人姓名
    private List<Employee> EmployeeList;

    /**
     * 当前登陆人的角色
     */
    private Integer roleId;
}
