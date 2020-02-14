package com.oa.entity.rule;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 规章类型
 *
 * @author ccl
 * @create 2017-01-04-17:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RuleType extends BaseEntity {
    @Like
    private String name;//类型名称

    private  Long sort;//排序
}
