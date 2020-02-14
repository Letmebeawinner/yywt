package com.houqin.entity.meeting;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class MeetingRepairRecord extends BaseEntity {

    //维护人
    private String name;
    //维护时间
    private Date repairTime;
    //设备情况
    private String description;
    //会场id
    private Long meetingId;
    //会场名称
    private String meetingName;

}
