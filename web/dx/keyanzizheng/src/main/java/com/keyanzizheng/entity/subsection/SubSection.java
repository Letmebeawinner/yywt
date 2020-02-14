package com.keyanzizheng.entity.subsection;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 处室
 * <p>教研部<p>
 *
 * @author YaoZhen
 * @date 12-20, 11:25, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SubSection extends BaseEntity {

    /**
     * 所属处室
     */
    private String name;

    /**
     * 关联系统用户id（各区党校）
     */
    private Long linkId;

}
