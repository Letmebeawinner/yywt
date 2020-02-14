package com.oa.entity.meetingapply;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会议申请拓展表
 *
 * @author ccl
 * @create 2017-02-14-15:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MeetingApplyDto extends Meetingapply{

    private String userName;//审核人

    private String meetingName;//会议室名称

}
