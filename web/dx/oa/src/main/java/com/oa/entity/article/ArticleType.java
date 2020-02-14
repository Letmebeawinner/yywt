package com.oa.entity.article;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章类型
 *
 * @author wanghailong
 * @create 2017-04-12-下午 3:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleType extends BaseEntity {
    String type_name;
}
