package com.oa.entity.article;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章基本属性
 *
 * @author wanghailong
 * @create 2017-04-12-下午 2:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseEntity {
     private Integer type_id;
     private String name;
     private String content;
}
