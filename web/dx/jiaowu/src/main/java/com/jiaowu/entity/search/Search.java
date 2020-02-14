package com.jiaowu.entity.search;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/15.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Search extends BaseEntity {
    private static final long serialVersionUID = -1990974085188574310L;

    private Long userId;

    private String userName;

    private Long classId;

    private String className;

    private Long unitId;

    private String unit;

    private String hotQuestion;

    private String solveQuestion;

    private String advice;

    private String other;
}
