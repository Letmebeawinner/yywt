package com.oa.entity.duty;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 值班
 *
 * @author lzh
 * @create 2017-01-07-15:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Duty extends BaseEntity {
    private Long sysUserId;//系统用户id
    private Date dutyTime;//值班时间
}
