package com.jiaowu.entity.materialType;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/28.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MaterialTypeDto extends MaterialType {
    private static final long serialVersionUID = -6940434743941364605L;
    //实报数量
    private Integer actualNum;
    //上报率
    private String reportrate;
}
