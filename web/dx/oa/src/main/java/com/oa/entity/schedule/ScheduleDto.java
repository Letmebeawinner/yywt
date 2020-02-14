package com.oa.entity.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 日程安排表
 *
 * @author ccl
 * @create 2017-02-10-10:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleDto extends ScheduleArrange{

    private String sysUserName;//用户名

    private String context;//内容

    private Date startTime;//开始时间

    private Long type;//类型



}
