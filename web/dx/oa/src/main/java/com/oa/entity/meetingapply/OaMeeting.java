package com.oa.entity.meetingapply;

import com.oa.annotation.Like;
import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会议申请记录
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaMeeting extends BaseAuditEntity {
    @Like
    private Long userId;//用户id
    private Long meetId;//会议室id
    @Like
    private String name;//会议名称

}
