package com.oa.entity.letter;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公文类型
 *
 * @author ccl
 * @create 2017-02-07-11:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LetterType extends BaseEntity{

    private String name;//名称

    private Long parentId;//父节点

    private Long sort;//排序
}
