package com.keyanzizheng.entity.employee;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 教职工信息
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Employee extends BaseEntity {

    private static final long serialVersionUID = -9074868674942544355L;
    private String employeeNo;//编号
    private String identityCard;//身份证
    private String mobile;//电话
    private String email;//邮箱
    private String name;//姓名
    private Integer sex;//性别 1 男 2 女
    private String nationality;//民族
    private String education;//学历
    private String profession;//专业
    private String position;//职务
    private String employeeTypeName;//职务
    private BigDecimal baseMoney;//基本工资
    private Integer ifCripple;//是否残疾
    private String resumeInfo;//履历信息
    private Long resultId;//成果id
    private Integer taskType;//课题类型
    private Long sysUserId;//系统用户id
}
