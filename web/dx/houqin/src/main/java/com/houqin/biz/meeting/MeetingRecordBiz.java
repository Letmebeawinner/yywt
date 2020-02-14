package com.houqin.biz.meeting;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.houqin.dao.meeting.MeetingRecordDao;
import com.houqin.entity.meeting.Meeting;
import com.houqin.entity.meeting.MeetingRecord;
import com.houqin.entity.meeting.MeetingRecordDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRecordBiz extends BaseBiz<MeetingRecord, MeetingRecordDao> {

    @Autowired
    private MeetingBiz meetingBiz;

    /**
     * 增加会议申请
     *
     * @param meetingRecord
     * @return 返回 1 代表有冲突 ，返回0 代表没有冲突
     */
    public int addMeetingApply(MeetingRecord meetingRecord) {
        boolean flag = meetingApplyTimeConflict(meetingRecord, "ADD");
        if (flag) {
            return 1;
        }
        this.save(meetingRecord);
        return 0;
    }

    /**
     * 修改申请会议室的时间
     *
     * @param meetingRecord
     * @return 1 代表有冲突，0 没
     */
    public int updateMeetRecord(MeetingRecord meetingRecord) {
        boolean flag = meetingApplyTimeConflict(meetingRecord, "UPDATE");
        if (flag) {
            return 1;
        }
        this.update(meetingRecord);
        return 0;
    }

    public List<MeetingRecordDto> convertMeetingToDto(List<MeetingRecord> meetingRecords) {
        if (meetingRecords == null || meetingRecords.size() == 0) {
            return null;
        }
        return meetingRecords.stream()
                .map(meetingRecord -> {
                    return convertToDto(meetingRecord);
                })
                .collect(Collectors.toList());
    }

    private MeetingRecordDto convertToDto(MeetingRecord meetingRecord) {
        MeetingRecordDto meetingRecordDto = new MeetingRecordDto();
        BeanUtils.copyProperties(meetingRecord, meetingRecordDto);
        if (meetingRecord.getMeetingId() != null) {
            Meeting meeting = meetingBiz.findById(meetingRecord.getMeetingId());
            meetingRecordDto.setName(meeting.getName());
        }
        return meetingRecordDto;
    }

    /**
     * 判断会议申请是否有时间上的冲突
     *
     * @param meetingRecord
     * @return true 有冲突，false没有冲突
     */
    private boolean meetingApplyTimeConflict(MeetingRecord meetingRecord, String operation) {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = smf.format(meetingRecord.getUseTime());
        String endTime = smf.format(meetingRecord.getTurnTime());
        String sql = " meetingId = " + meetingRecord.getMeetingId() + " and status != 1"
                + " and ((useTime <='" + startTime + "' and turnTime >='" + endTime + "')"
                + " or ( useTime >='" + startTime + "'" + " and useTime <= '" + endTime + "')"
                + " or ( turnTime >='" + startTime + "'" + " and turnTime <= '" + endTime + "')"
                + " or ( useTime >='" + startTime + "'" + " and turnTime <= '" + endTime + "')"
                + ")";
        //如果是更新判断
        if ("UPDATE".equals(operation)) {
            sql += " and id != " + meetingRecord.getId();
        }
        List<MeetingRecord> records = this.find(null, sql);
        return (records != null && records.size() > 0);
    }

    /**
     * 选择会场前，排查会场使用状态
     */
    public void changeMeetingStatusWhenChose() {
        List<Meeting> meetingList = meetingBiz.find(null, " 1=1 and status !=1");
        if (ObjectUtils.isNotNull(meetingList)) {
            for (Meeting meeting : meetingList) {
                Long meetingId = meeting.getId();
                List<MeetingRecord> meetingRecords = this.find(null, " meetingId=" + meetingId + " and status=0");
                if (meetingRecords.size() > 0) {
                    for (MeetingRecord meetingRecord : meetingRecords) {
                        Date turnTime = meetingRecord.getTurnTime();
                        Date nowDate = new Date();
                        int compareTo = nowDate.compareTo(turnTime);
                        // 会场的使用结束时间小于当前时间，修改为取消状态
                        if (compareTo > 0) {
                            MeetingRecord meetingRecord1 = this.findById(meetingRecord.getId());
                            meetingRecord1.setStatus(1);
                            this.update(meetingRecord1);
                        }
                    }
                } else {
                    Meeting meeting1 = meetingBiz.findById(meetingId);
                    meeting1.setStatus(0);
                    meetingBiz.update(meeting1);
                }
            }
        }
    }

    /**
     * 判断这个时间段是否有使用的
     *
     * @return true 有冲突，false没有冲突
     */
    public boolean whetherToUseDuringTheTimePeriod(MeetingRecord meetingRecord, String operation) {
        boolean flag = meetingApplyTimeConflict(meetingRecord, operation);
        return flag;
    }
}
