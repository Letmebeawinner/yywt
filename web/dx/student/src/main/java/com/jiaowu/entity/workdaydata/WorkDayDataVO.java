package com.jiaowu.entity.workdaydata;

import lombok.Data;

import java.util.List;

/**
 * 考勤记录
 *
 * @author YaoZhen
 * @date 04-04, 16:26, 2018.
 */
@Data
public class WorkDayDataVO {

    /**
     * 打卡记录
     */
    List<WorkDayDataNode> nodes;
    /**
     * 对应的basePersonId
     */
    private Long personId;
    /**
     * 人员姓名
     */
    private String name;
    /**
     * 卡号
     */
    private String cardNo;
}
