package com.renshi.entity.union;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工会小组
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class UnionEmployee extends BaseEntity {

    private static final long serialVersionUID = 9179336134531062026L;
    private Long unionId;//工会ID
    private Long employeeId;//教职工ID
    private Long position;//职务


    private String employeeName;//员工姓名
    private Integer sex;//性别
    private String mobile;//手机号
    private String nationality;//民族

    private Integer num;


}
