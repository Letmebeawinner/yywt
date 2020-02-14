package com.jiaowu.entity.classes;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClassesStatistic extends BaseEntity{


    private Long classId;//班级id

    private String name;//班级名称

    private int num;//报名人数

    private int unitId;//报名人数

    private int classNum;//ban

    private String classTypeName;


}
