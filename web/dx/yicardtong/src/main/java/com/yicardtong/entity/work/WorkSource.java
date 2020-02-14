package com.yicardtong.entity.work;

import lombok.Data;

import java.util.Date;

/**
 * 考勤源数据
 *
 * @author YaoZhen
 * @date 04-25, 17:15, 2018.
 */
@Data
public class WorkSource {

    /**
     * 人员的考勤卡号
     */
    private String cardNo;

    /**
     * 人员姓名
     */
    private String perName;

    /**
     * 打卡dataTime
     * 2017-11-04 11:20:11.000
     */
    private Date srcDateTime;

    /**
     * 打卡time
     * 11:20:11
     */
    private String srcTime;

    /**
     * 打卡date
     * 2017-11-04
     */
    private String srcDate;

    /*
        1、迟到，上午打卡超过8:31，下午打卡超过14:31，或者没打上班卡
        2、早退，上午打卡早于10:50，下午打卡早于16:00，或者没有打下班卡
        3、缺旷：未打卡
        4、请假：有请假情况的缺旷记成请假
     */

}
