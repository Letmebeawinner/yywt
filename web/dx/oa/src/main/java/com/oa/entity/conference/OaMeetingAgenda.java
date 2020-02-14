package com.oa.entity.conference;

import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 议程
 * 议程中下拉选择议题
 *
 * @author YaoZhen
 * @create 11-24, 10:58, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaMeetingAgenda extends BaseAuditEntity {
    /**
     * 议程时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd HH-mm-ss")
    private Date time;

    /**
     * 议程地点
     */
    private String location;

    /**
     * 2018-10-16
     * 议程地点,会场id
     */
    private Long locationId;

    /**
     * 议程主持人
     */
    private String compere;

    /**
     * 议程出席
     */
    private String bePresent;

    /**
     * 议程缺席
     */
    private String absent;

    /**
     * 议程列席
     */
    private String attend;
    /**
     * 议程记录
     */
    private String record;
    /**
     * <p>议题</p>Id
     *
     * @see OaMeetingTopic
     */
    private String topicIds;
    /**
     * <p>议题</p>名称
     *
     * @see OaMeetingTopic
     */
    private String name;

    /**
     * 年份
     */
    private String year;

    /**
     * 次数
     */
    private String frequency;
}
