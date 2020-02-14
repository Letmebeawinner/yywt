package com.oa.entity.news;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新闻部门关联表
 *
 * @author lzh
 * @create 2016-12-30-14:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewsDepartment extends BaseEntity{
    private Long newsId;//新闻id
    private Long departmentId;//部门id
}
