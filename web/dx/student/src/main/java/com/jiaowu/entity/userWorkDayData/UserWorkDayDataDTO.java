package com.jiaowu.entity.userWorkDayData;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 李帅雷 on 2017/10/19.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserWorkDayDataDTO extends UserWorkDayData implements Serializable {
    /**
     * 开班时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd hh-mm-ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd hh-mm-ss")
    private Date endTime;


}
