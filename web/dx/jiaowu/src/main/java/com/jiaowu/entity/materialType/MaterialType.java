package com.jiaowu.entity.materialType;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/25.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MaterialType extends BaseEntity {
    private static final long serialVersionUID = 4014127524181492867L;
    //名称
    private String name;
    //上传数量
    private Integer num;
}
