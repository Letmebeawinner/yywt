package com.houqin.dao.meeting;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.meeting.MeetingRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface MeetingRecordDao extends BaseDao<MeetingRecord> {

    List<Map<String, Object>> getMeetingRecordByYear(@Param("time") String time);

    List<Map<String, String>> getMeetingRecordDetailByYearAndMonth(@Param("year") String year, @Param("month") String month);

    List<Map<String, Object>> getMeetingRecordGroupByClassesId(@Param("time") String time);
}
