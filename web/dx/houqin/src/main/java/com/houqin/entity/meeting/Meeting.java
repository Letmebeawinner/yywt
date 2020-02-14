package com.houqin.entity.meeting;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * h会场
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Meeting extends BaseEntity{

    @Like
    private String name;

    private Date turnTime;

    private Date useTime;
    /**
     * 可容纳人数
     */
    private Long peopleNo;
}
