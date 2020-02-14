package com.renshi.biz.attendance;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.dao.attendance.WorkStatisticsDao;
import com.renshi.entity.attendance.WorkAttendance;
import com.renshi.entity.attendance.WorkStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 上班考勤统计Biz
 *
 * @author 268
 */
@Service
public class WorkStatisticsBiz extends BaseBiz<WorkStatistics, WorkStatisticsDao> {
    @Autowired
    private WorkAttendanceBiz workAttendanceBiz;
    /**
     * 年月日查询统计数据
     *
     * @author 268
     */
    public List<WorkStatistics> getWorkStatisticsList(Pagination pagination,String year,String month,String day) {
        String sqlString=" 1=1 ";
        if(!StringUtils.isTrimEmpty(year)){
            if(!StringUtils.isTrimEmpty(month)){
                if(month.length()==1){
                    month="0"+month;
                }
                if(!StringUtils.isTrimEmpty(day)){
                    if(day.length()==1){
                        day="0"+month;
                    }
                    sqlString+=" and workDate like '"+year+"-"+month+"-"+day+"'";
                }else{
                    sqlString+=" and left(workDate,7) like '"+year+"-"+month+"'";
                }
            }else{
                sqlString+=" and left(workDate,4) like '"+year+"'";
            }
        }
        return this.find(pagination,sqlString);
    }
    /**
     * 定时刷新统计数据
     *
     * @author 268
     */
    public void addWorkStatistics() {
        Integer count = this.count(" 1=1 ");
        if (count==0) {
            List<WorkAttendance> workAttendanceList = workAttendanceBiz.findAll();
            List<String> list=new ArrayList<>();
            if(!CollectionUtils.isEmpty(workAttendanceList)){
                for(WorkAttendance workAttendance:workAttendanceList){
                    list.add(workAttendance.getWorkDate().substring(0,10));
                }
                List<String> dateList=new ArrayList<>();
                for(String time:list){
                    if(Collections.frequency(dateList,time)<1){dateList.add(time);}
                }
                for(String date:dateList){
                    String aqlString = " 1=1 and left(workDate,10) like '" + date + "' ";
                    Integer lateCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=1");
                    Integer earlyCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=2");
                    Integer absentCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=3");
                    Integer leaveCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=4");
                    Integer wkCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=5");
                    Integer overCount = workAttendanceBiz.count(aqlString + " and workStatus=2");
                    WorkStatistics workStatistics = new WorkStatistics();
                    workStatistics.setWorkDate(date);
                    workStatistics.setLateCount(lateCount);
                    workStatistics.setEarlyCount(earlyCount);
                    workStatistics.setAbsentCount(absentCount);
                    workStatistics.setLeaveCount(leaveCount);
                    workStatistics.setWkCount(wkCount);
                    workStatistics.setOverCount(overCount);
                    this.save(workStatistics);
                }
            }
        } else {
            String aqlString = " 1=1 and left(workDate,10) like '" + DateUtils.format(new Date(), "yyyy-MM-dd") + "' ";
            Integer lateCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=1");
            Integer earlyCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=2");
            Integer absentCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=3");
            Integer leaveCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=4");
            Integer wkCount = workAttendanceBiz.count(aqlString + " and workStatus=1 and workStatusType=5");
            Integer overCount = workAttendanceBiz.count(aqlString + " and workStatus=2");
            WorkStatistics workStatistics = new WorkStatistics();
            workStatistics.setWorkDate(DateUtils.format(new Date(), "yyyy-MM-dd"));
            workStatistics.setLateCount(lateCount);
            workStatistics.setEarlyCount(earlyCount);
            workStatistics.setAbsentCount(absentCount);
            workStatistics.setLeaveCount(leaveCount);
            workStatistics.setWkCount(wkCount);
            workStatistics.setOverCount(overCount);
            this.save(workStatistics);
        }
    }
}
