package com.oa.entity.conference;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 议题
 *
 * @author YaoZhen
 * @create 10-23, 10:26, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MeetingTopic extends BaseEntity {
    /**
     * 议题名称
     */
    private String name;

    /**
     * 紧急程度
     */
    private String emergencyDegree;

    /**
     * 汇报人
     */
    private String reporter;

    /**
     * 列席人
     */
    private String attendPeople;

    /**
     * 议题内容
     */
    private String subjectContent;
}
