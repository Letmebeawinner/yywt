package com.jiaowu.entity.repair;

import com.jiaowu.entity.sysuser.SysUser;
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

    //private String userName;//用户名

    private String techName;//报修人

    //private SysUser sysUser;//维修人员信息
}
