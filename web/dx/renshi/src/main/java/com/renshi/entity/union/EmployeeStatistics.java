package com.renshi.entity.union;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工会人员统计
 * Created by 268 on 2017/2/6.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeStatistics extends BaseEntity {

    private static final long serialVersionUID = -687288652350252154L;
    private String unionName;//工会处室名称
    private Long empNumber;//会员人数
    private Long manNumber;//男性人数
    private Long womanNumber;//女性人数
    private Long ageAvg;//平均年龄
}
