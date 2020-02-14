package com.sms.entity.employee;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Family extends BaseEntity{
    /**
     * 教职工id
     */
    private Long employeeId;

    /**
     * 称谓
     */
    private String title;

    /**
     * 姓名
     */
    private String name;

    /**
     * 出生年月
     */
    private Date birthday;

    /**
     * 政治面貌
     */
    private String aspect;
    /**
     * 单位
     */
    private String unit;


}
