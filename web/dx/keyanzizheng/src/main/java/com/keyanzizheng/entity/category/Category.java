package com.keyanzizheng.entity.category;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成果类型
 * <p>
 * 成果类型下的成果类别
 * 一个成果类别下 对应多个成果类型
 * <p>
 *
 * @author YaoZhen
 * @date 12-20, 14:37, 2017.
 * @see com.keyanzizheng.entity.result.ResultForm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends BaseEntity {

    /**
     * 成果类别id
     */
    private Long resultFormId;

    /**
     * 成果类型名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

}
