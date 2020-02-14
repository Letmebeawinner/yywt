package com.renshi.entity.retirement;

import com.renshi.entity.employee.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 离退休
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRetirement extends Retirement {

    private static final long serialVersionUID = 4575355419453402582L;
    private String employeeName; //教职工姓名
    private Employee employee; //教职工


}
