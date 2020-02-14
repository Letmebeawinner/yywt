package com.houqin.entity.meeting;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 会场使用记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MeetingRecord extends BaseEntity {

    private Long userId;//用户id

    private String caption;//标题
    private String userName;//使用人

    private Long classesId;//班级id

    private String classesName;//班级名称

    private Long meetingId;//会议室id

    private Date useTime;//会场使用时间

    private Date turnTime;//会场结束时间

    private String description;//备注说明


    //會場開始時間，轉化陳格林時間
    private String startTimeGmt;
    //會場結束時間，轉化陳格林時間
    private String endTimeGmt;

    public String getStartTimeGmt() {
        if (useTime == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return this.startTimeGmt = sdf.format(useTime);
    }

    public String getEndTimeGmt() {
        if (turnTime == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return this.endTimeGmt = sdf.format(turnTime);
    }

}
