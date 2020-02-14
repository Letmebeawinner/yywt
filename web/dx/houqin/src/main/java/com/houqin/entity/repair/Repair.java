package com.houqin.entity.repair;

import com.a_268.base.core.BaseEntity;
import com.houqin.entity.sysuser.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 维修类型
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Repair extends BaseEntity {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 类型id
     */
    private Long typeId;
    /**
     * 维修名称
     */
    private String name;
    /**
     * 维修编号
     */
    private String number;
    /**
     * 内容
     */
    private String context;
    /**
     * 处理结果
     */
    private String result;
    /**
     * 技术员
     */
    private Long techId;
    /**
     * 维修时间
     */
    private Date repairTime;
    /**
     * 审核状态
     */
    private Long check;

    /**
     * 处理截至时间
     */
    private Date warnTime;

    private String userName;

    private Long pepairPeopleId;//维修人员

    private SysUser sysUser;//维修人员信息

    /**
     * 响应时间评价
     * 1满意 0不满意
     */
    private Integer responseTime;

    /**
     * 维修质量评价
     * 1满意 0不满意
     */
    private Integer quality;

    /**
     * 人员态度评价
     * 1满意 0不满意
     */
    private Integer attitude;

    /**
     * 意见栏
     */
    private String commentsBelow;

    /**
     * 维修地点
     */
    private String repairSite;

    /**
     * 报修电话
     */
    private String telephone;

    /**
     * 损耗品
     */
    private String lossGoods;

    /**
     * 作用范围
     */
    private Long functionType;
}
