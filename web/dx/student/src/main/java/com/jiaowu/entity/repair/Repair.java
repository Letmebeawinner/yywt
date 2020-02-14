package com.jiaowu.entity.repair;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.sysuser.SysUser;
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
     * 预警时间
     */
    private Date warnTime;

    private String userName;

    private Long pepairPeopleId;//维修人员

    private SysUser sysUser;//维修人员信息
}
