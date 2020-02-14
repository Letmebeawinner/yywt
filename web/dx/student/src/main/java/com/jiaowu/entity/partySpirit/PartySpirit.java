package com.jiaowu.entity.partySpirit;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/9.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PartySpirit extends BaseEntity {
    private static final long serialVersionUID = 8394939302894957902L;
    //班型ID
    private Long classTypeId;
    //班次ID
    private Long classId;
    //用户ID
    private Long userId;
    //名称
    private String name;
    //个人党性分析材料
    private Double personMaterial;
    //组织纪律
    private Double organisation;
    //综合表现
    private Double allPerformance;
    //总分
    private Double total;
    //备注
    private String note;
}
