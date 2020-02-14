package com.jiaowu.entity.studyTest;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/23.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class StudyTest extends BaseEntity {
    private static final long serialVersionUID = 8414422740275841938L;
    //班型ID
    private Long classTypeId;
    //班次ID
    private Long classId;
    //用户ID
    private Long userId;
    //名称
    private String name;
    //毕（结）业证号
    private String graduateNumber;
    //在线学习
    private Double onlineStudy;
    //调研报告
    private Double searchReport;
    //毕业考试
    private Double graduateTest;
    //总分
    private Double total;
    //备注
    private String note;
}
