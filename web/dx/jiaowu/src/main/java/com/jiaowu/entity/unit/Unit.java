package com.jiaowu.entity.unit;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/1.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Unit extends BaseEntity {
    private static final long serialVersionUID = 5063979222355714179L;
    //名称
    private String name;
    //排序值
    private Integer sort;

    private Integer num = 0;

    private Integer classNum = 0;
}