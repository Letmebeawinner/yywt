package com.houqin.entity.repair;

import com.houqin.entity.pepairPeople.PepairPeople;
import com.houqin.entity.sysuser.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 报修拓展表
 *
 * @author ccl
 * @create 2016-12-20-18:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RepairDto extends Repair {

    private String typeName;//类型

    private String techName;//报修人
}
