package com.oa.biz.meeting;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.meeting.MeetingDao;
import com.oa.entity.meeting.Meeting;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会议室
 *
 * @author ccl
 * @create 2016-12-27-17:28
 */
@Service
public class MeetingBiz extends BaseBiz<Meeting,MeetingDao>{

    public List<Meeting> meetingList(){
        List<Meeting> meetings=this.find(null,"1=1");
        return meetings;
    }

}
