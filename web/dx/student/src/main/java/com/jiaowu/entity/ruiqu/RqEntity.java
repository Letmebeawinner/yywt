package com.jiaowu.entity.ruiqu;

import lombok.Data;

import java.util.Date;


/**
 * 锐取的实体
 *
 * @author YaoZhen
 * @date 04-03, 18:18, 2018.
 */
@Data
public class RqEntity {
    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教室ID
     */
    private Long classroomId;

    /**
     * 教室名称
     */
    private String classroomName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
