package com.jiaowu.entity.classes;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by caichenglong on 2017/10/20.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassTypeStatistic extends BaseEntity {


    private int num;//报名人数

    private int classTypeId;//班型id

    private String className;//班型名称

}
