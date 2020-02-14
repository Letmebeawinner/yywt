package com.oa.entity.schedule;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 日程安排表
 *
 * @author ccl
 * @create 2017-01-20-10:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleArrange extends BaseEntity{

    private Long receiverId;//接收人id

    private Long scheduleId;//日程id

}
