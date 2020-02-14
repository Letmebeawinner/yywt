package com.jiaowu.entity.sysuser;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 教职工信息
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Employee extends BaseEntity implements Serializable{
    private Long employeeType;//教职工类别 1是教师 2是职工 3是其它`
    private String name;//姓名
    private String picture;//照片
    private Integer sex;//性别 1 男 0 女
    private Date birthDay;//出生日期
    private Long age;//年龄
    private String nationality;//民族
    private String nativePlace;//籍贯
    private String birthdayPlace;// 出生地
    private Date enterPartyTime;//入党时间
    private Date workTime;//参加工作时间
    private String identityCard;//身份证号
    private String profressTelnel;//专业技术职务
    private String speciality;//特长
    private String education;//全日制学历学位
    private String profession;//全日制毕业院校及专业
    private String jobEducation;//在职教育学历学位
    private String jobProfession;//在职教育毕业院校及专业
    private String presentPost;//现任职务
    private String mobile;//手机号
    private Long sysUserId;//系统用户id

    private Long type;//0是教师 1是班主任
    private Integer source;//来源,1代表本校,2代表外校.
    private Integer teachingResearchDepartment;//教研处

    /*//李帅雷修改
    private String identityCard;//身份证
    private String resumeInfo;//履历信息*/

    private String category;//类别
    private String level;//级别
    private String appointTime;//现任时间
    private String officeTime;//同职责时间
    private String description;//备注
    private String department;//部门名称
    private String cardNo;//一卡通编号

}
