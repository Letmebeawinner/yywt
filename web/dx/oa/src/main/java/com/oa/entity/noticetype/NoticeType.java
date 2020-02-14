package com.oa.entity.noticetype;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公告类型
 *
 * @author ccl
 * @create 2016-12-29-9:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeType extends BaseEntity {
    @Like
    private String name;//类型名称

    private Long sort;//排序

}
