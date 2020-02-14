package com.oa.entity.news;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新闻类型实体
 *
 * @author lzh
 * @create 2016-12-28-17:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewsType extends BaseEntity{
    @Like
    private String name;//新闻类型名
}
