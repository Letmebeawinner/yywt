package com.houqin.entity.repair;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 维修统计表
 * Created by Administrator on 2017/11/13 0013.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RepairStatistics {

    private Integer year;//年份
    private Integer month;//月份
    private Integer count;//总数
    private Integer yiMaintenance;//已维修
    private Integer weiMaintenance;//未维修
    private String name;//类型名称
    private Integer typeId;//类型id
}
