package com.oa.entity.meetingapply;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 会议申请记录
 *
 * @author ccl
 * @create 2016-12-28-10:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Meetingapply extends BaseEntity {
    @Like
    private Long userId;//用户id

    private Long sysUserId;//审核人

    private Long meetId;//会议室id
    @Like
    private String name;//会议名称

    private Date applyStartDate;//申请开始时间

    private Date applyendDate;//申请结束时间

    private String context;//备注

}
