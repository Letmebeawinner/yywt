package com.jiaowu.entity.teacher;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 教师
 *
 * @author 李帅雷
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Teacher extends BaseEntity {
    private static final long serialVersionUID = 4339303632548084212L;
    private String employeeNo;//编号
    //    private String identityCard;//身份证
    private String name;//姓名
    private Long age;//年龄
    private Integer sex;//性别 1 男 0 女
    private String nationality;//民族
    private String education;//学历
    private String profession;//专业
    private String position;//职务
    /*private BigDecimal baseMoney;//基本工资*/
    /*    private Integer ifCripple;//是否残疾*/
    private Integer politicalStatus; //政治面貌

    //    private String resumeInfo;//履历信息
    private Long employeeType;//教职工类别
    private Date birthDay;//出生日期

    private Long type;//0默认值 1是班主任 2教师

    private Integer source;//来源,1代表本校,2代表外校.

    // 教研部
    // 1:党史
    // 2.公管
    // 3.经济学
    // 4.法学
    // 5.文化与社会发展
    // 6.马列
    // 7.统一战线
    private Integer teachingResearchDepartment;

    private String unitDepartment;//外校 手动输入单位（部门）

}
