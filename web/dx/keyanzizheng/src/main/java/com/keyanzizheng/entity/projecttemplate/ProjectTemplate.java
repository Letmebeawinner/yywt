package com.keyanzizheng.entity.projecttemplate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课题模板
 *
 * @author YaoZhen
 * @date 11-14, 19:39, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectTemplate extends BaseEntity {
    /**
     * 标题
     */
    private String title;

    /**
     * 附件地址
     */
    private String url;
}
