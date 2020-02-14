package com.oa.entity.article;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章类的扩展
 *
 * @author wanghailong
 * @create 2017-04-13-下午 2:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleDto extends Article {
    private String newArticleTypeName;
}
