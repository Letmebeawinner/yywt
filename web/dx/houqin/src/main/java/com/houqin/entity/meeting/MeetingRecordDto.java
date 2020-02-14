package com.houqin.entity.meeting;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会场使用记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MeetingRecordDto extends MeetingRecord {
    //会议室名
    private String name;
}
