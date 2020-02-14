package com.renshi.entity.union;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 职位
 * Created by 268 on 2016/12/28.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Position extends BaseEntity {

    private static final long serialVersionUID = 8144086222592150558L;
    private String name; //职位名称
    private String duty; //职责
    private String remark; //备注
}
