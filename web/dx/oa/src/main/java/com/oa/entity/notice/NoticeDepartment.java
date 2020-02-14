package com.oa.entity.notice;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新闻部门关联表
 *
 * @author lzh
 * @create 2016-12-30-14:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeDepartment extends BaseEntity {
    private Long noticeId;//公告id
    private Long departmentId;//部门id
}
