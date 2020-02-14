package com.oa.entity.conference;

import com.oa.annotation.Like;
import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OA 议题
 * 议程中下拉选择议题
 *
 * @author YaoZhen
 * @create 11-24, 10:44, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaMeetingTopic extends BaseAuditEntity {
    /**
     * 议题名称
     */
    @Like
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

    /**
     * 使用状态 0未确认 1已确认未使用 2已使用
     */
    private Integer state;

    //附件地址
    private String fileUrl;
    //附件名
    private String fileName;
    private String timeStamp;//时间戳


    //关联议程
    private OaMeetingAgenda oaMeetingAgenda;

    //数字转汉字
   private String numStr;
   //是否可以查看
   private Boolean isLook;

}
