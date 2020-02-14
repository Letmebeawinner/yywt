package com.houqin.entity.meeting;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MeetingStatistic extends BaseEntity{

    //年
    private int year;
    //月
    private int month;
    //总数
    private float amount;

}
