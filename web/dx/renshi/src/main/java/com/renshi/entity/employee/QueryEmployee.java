package com.renshi.entity.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教职工子类
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryEmployee extends Employee {

    private static final long serialVersionUID = -2518070611191041339L;
    private String email;//邮箱
    private String password;//系统登录密码
    private String userNo;//系统登录号码
    private Long educateId;   //培训项目id
    private Long unionId;   //工会
    private Long countrysideId;   //下乡项目id
    private String departmentName;//部门
    private String unionPosition;//工会职位
    private Integer joinStatus;//下乡活动参与状态

}
