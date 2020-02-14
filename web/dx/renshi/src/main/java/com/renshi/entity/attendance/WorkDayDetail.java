package com.renshi.entity.attendance;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上班考勤
 * Created by 268 on 2017/3/2.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkDayDetail{

    private static final long serialVersionUID = -4124436534919236755L;
    //时间
   private String workDate;
    //员工编号
    private String basePerID;
    //早上开始时间
    private String morBeg;
    //早上结束时间
    private String morEnd;
    //下午开始时间
    private String aftBeg;
    //下午结束时间
    private String aftEnd;
    //晚上开始时间
    private String eveBeg;
    //晚上结束时间
    private String eveEnd;
    //自定义项
   private String define1;
   private String define2;
   private String cMemo;
   private String lateMins;//迟到分钟
   private String lateNuM;//迟到次数
   private String earlyMins;//早退分钟
   private String earlyNuM;//早退次数
   private String absentDay;//旷工天数
   private String absentHr;//矿工小时
   private String overMins;//加班分钟
   private String overHr;//加班小时
   private String holOverDs;//节日加班天数
   private String holOverHs;//节日加班小时
   private String satOverDs;//周末加班天数
   private String satOverHs;//周末加班小时
   private String normalOverDs;//平时加班天数
   private String normalOverHs;//平时加班小时
   private String normalOverMins;//平时加班分钟
   private String leaveHs;//请假小时
   private String leaveDs;//请假天数
   private String leaveName;//请假类型
   private String wkHrs;//出勤小时
   private String wkDays;//出勤天数
   private String holName;//节假日名称
   private String adjHours;//调休小时
}
