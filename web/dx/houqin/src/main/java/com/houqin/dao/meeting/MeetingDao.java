package com.houqin.dao.meeting;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.meeting.Meeting;
import com.houqin.entity.meeting.MeetingRecord;
import com.houqin.entity.meeting.MeetingStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeetingDao extends BaseDao<Meeting> {

    /**
     * 查询全校的每年的汇总数
     * @param whereSql
     * @return
     */
    public Integer querySchoolMeetingRecordNum(@Param("whereSql") String whereSql);

    /**
     * 查询按年统计
     * @param whereSql
     * @return
     */
    public List<MeetingStatistic>  queryMeetingRecordByYear(@Param("whereSql") String whereSql);


    /**
     * 查询详细统计
      * @param month
     * @param year
     * @return
     */
    public MeetingRecord queryMeetingRecordDetailStatistic(@Param("month") String month,@Param("year") String year);



}
