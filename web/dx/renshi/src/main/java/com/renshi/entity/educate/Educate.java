package com.renshi.entity.educate;

import com.a_268.base.core.BaseEntity;
import com.renshi.annotation.DATE;
import com.renshi.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 培训进修
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Educate extends BaseEntity {

    //教职工id
    private Long employeeId;
    //职称
    private String technical;
    //培训名称
    @Like
    private String name;
    //培训开始时间
//    @DATE(start = true,value = "beginTime")
    private Date beginTime;
    //培训结束时间
//    @DATE(start = false,value = "endTime")
    private Date endTime;
    //总学习日
    private Integer studay;
    //请假天数
    private Integer leavedays;
    //培训人数
    private Integer actstuday;
    //调训单位
    private String diaoXunUnit;
    //培训地点
    private String trainingLocation;
    //培训单位
    private String trainingUnit;
    //是否发放毕业证书
    private Integer leavingCertificate;
    //总结
    private String summarize;
    //天数
    private String description;

    /**
     * 员工名称
     */
    private String employeeName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 入党时间
     */
    private Date enterPartyTime;
    /**
     * 工作时间
     */
    private Date workTime;
    /**
     * 职务
     */
    private String presentPost;

}
