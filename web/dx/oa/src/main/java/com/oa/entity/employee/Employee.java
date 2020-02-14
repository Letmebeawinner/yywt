package com.oa.entity.employee;

import com.a_268.base.core.BaseEntity;
import com.a_268.base.util.ObjectMapperUtils;
import com.a_268.base.util.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.ObjectUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private String sex;//性别 1 男 0 女
    private String nationality;//民族
    private String education;//学历
    private String profession;//专业
    private Long departMentId;//部门id
    private String departMentName;//部门
    private String position;//职务
    private BigDecimal baseMoney;//基本工资
    private Integer ifCripple;//是否残疾
    private String resumeInfo;//履历信息
    private Long taskId;//课题名
    private Integer taskType;//课题类型
    private String workTime;//参加工作时间

    private Integer flag;

    public Employee() {
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Employee(Map<String, String> map) {
        this.setId(Long.valueOf(map.get("id")));
        this.setEmployeeNo(map.get("employeeNo"));
        this.setIdentityCard(map.get("identityCard"));
        this.setMobile(map.get("mobile"));
        this.setEmail(map.get("email"));
        this.setName(map.get("name"));
        this.setSex(map.get("sex"));
        this.setNationality(map.get("nationality"));
        this.setEducation(map.get("education"));
        this.setProfession(map.get("profession"));
        if (!StringUtils.isEmpty(map.get("departMentId"))) {
            this.setDepartMentId(Long.valueOf(map.get("departMentId")));
        }
        this.setDepartMentName(map.get("departmentName"));
        this.setPosition(map.get("presentPost"));
        if (!StringUtils.isEmpty(map.get("baseMoney"))) {
            this.setBaseMoney(BigDecimal.valueOf(Double.valueOf(map.get("baseMoney"))));
        }
        if (!StringUtils.isEmpty(map.get("ifCripple"))) {
            this.setIfCripple(Integer.valueOf(map.get("ifCripple")));
        }
        if (!StringUtils.isEmpty(map.get("workTime"))) {
            this.setWorkTime(map.get("workTime"));
        }
        this.setResumeInfo(map.get("resumeInfo"));
        if(!StringUtils.isEmpty(map.get("age"))){
            this.setAge(Long.parseLong(map.get("age")));
        }
        this.setEmployeeType(Long.parseLong(map.get("employeeType")));
        this.setPicture(map.get("picture"));
        this.setNativePlace(map.get("nativePlace"));
        this.setBirthdayPlace(map.get("birthdayPlace"));
        this.setProfressTelnel(map.get("profressTelnel"));
        this.setSpeciality(map.get("speciality"));
        this.setPresentPost(map.get("presentPost"));
        this.setJobEducation(map.get("jobEducation"));
        this.setJobProfession(map.get("jobProfession"));
        try {
            if(com.a_268.base.util.ObjectUtils.isNotNull(map.get("enterPartyTime"))){
                this.setEnterPartyTime(dateFormat.parse(map.get("enterPartyTime")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private String jobEducation;//在职教育学历学位
    private String jobProfession;//在职教育毕业院校及专业
    private Long sysUserId;//系统用户id
    private Long type;//0是教师 1是班主任
    private Date birthDay;//出生日期
    private Long age;//年龄
    private Long employeeType;//教职工类别 1是教师 2是职工 3是其它`
    private String picture;//照片
    private String nativePlace;//籍贯
    private String birthdayPlace;// 出生地
    private Date enterPartyTime;//入党时间
    private String profressTelnel;//专业技术职务
    private String speciality;//特长
    private String presentPost;//现任职务
}
