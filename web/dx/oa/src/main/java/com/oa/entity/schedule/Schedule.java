package com.oa.entity.schedule;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日程
 *
 * @author ccl
 * @create 2017-01-17-18:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseEntity {

    private Long senderId;//发送人id

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;//开始时间

    private Long type;//类型

    private String context;//内容

}
