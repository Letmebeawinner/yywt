package com.houqin.entity.oil;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by caichenglong on 2017/10/20.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OilStatistic extends BaseEntity {
    //年
    private int year;
    //月
    private int month;
    //总数
    private float amount;

}
