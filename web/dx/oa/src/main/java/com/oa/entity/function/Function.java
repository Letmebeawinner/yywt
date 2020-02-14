package com.oa.entity.function;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Cut;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能列表
 *
 * @author ccl
 * @create 2017-01-16-11:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Function extends BaseEntity {

    @Like
    private String name;//功能名称

    private String link;//功能连接

    private Long sort;//排序
    @Cut
    private int flag;

}
