package com.oa.entity.conference;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 议程
 *
 * @author YaoZhen
 * @create 10-23, 10:49, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Agenda extends BaseEntity {
    /**
     * 时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd HH-mm-ss")
    private Date time;

    /**
     * 地点
     */
    @Like
    private String location;

    /**
     * 主持人
     */
    @Like
    private String compere;

    /**
     * 出席
     */
    private String bePresent;

    /**
     * 缺席
     */
    private String absent;

    /**
     * 列席
     */
    private String attend;
    /**
     * 记录
     */
    private String record;
    /**
     * 议题
     */
    private  String agendaName;

    //议题id
    private Long topicId;



}
