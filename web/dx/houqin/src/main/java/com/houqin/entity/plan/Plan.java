package com.houqin.entity.plan;

import com.a_268.base.core.BaseEntity;
import com.houqin.entity.mess.Mess;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 就餐计划
 *
 * @author wanghailong
 * @create 2017-07-25-下午 3:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Plan extends BaseEntity {
    private String requ;//就餐要求
    private Long classId;//班次
    private Long classType;//班型
    private Long people;//人数
    private String standard;//标准
    private String numberPeople;//早中晚人数
    private Long messId;//食堂id
    private Long areaId;//食堂区域id
    private Mess mess;
    private Date planTime;

    /**
     * 添加就餐计划
     * 早餐人数
     */
    private Long moringpeople;
    /**
     * 早餐时间
     */
    private String moringtime;
    /**
     * 午餐人数
     */
    private Long noonpeople;
    /**
     * 午餐时间
     */
    private String noontime;
    /**
     * 晚餐人数
     */
    private Long dinnerpeople;
    /**
     * 晚餐时间
     */
    private String dinnertime;

    /**
     * 特殊说明
     */
    private String instructions;
}
