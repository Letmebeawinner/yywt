package com.jiaowu.entity.classes;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by caichenglong on 2017/10/20.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassTypeStatistic extends BaseEntity {


    private int num;//报名人数

    private int classTypeId;//班型id

    private String classTypeName;//班型名称

    private int classId;//班次id

    private String className;//班次名称

    private Integer age;//年龄
    private Integer ageOne;

    private Integer business;//级别
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date endTime;//结束时间

    //数据没有加此字段，只是为了在xml中查询只用
    private Long politicalStatus;//政治面貌

}
