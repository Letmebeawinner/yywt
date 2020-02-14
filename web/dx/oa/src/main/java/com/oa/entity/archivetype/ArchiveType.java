package com.oa.entity.archivetype;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 档案类型
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArchiveType extends BaseEntity {
    //名称
    private String name;

    //排序
    private Long sort;


}
