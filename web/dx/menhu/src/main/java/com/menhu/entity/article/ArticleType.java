package com.menhu.entity.article;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * descriPtion:资讯类型操作类
 *
 * @author guoshiqi
 * @create 2016-12-09-9:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleType extends BaseEntity{
    private static final long serialVersionUID = -6034634689029517089L;

    /**资讯类型父id**/
    private Long parentId;
    /**资讯类型名称**/
    private String name;
    /**资讯类型排序**/
    private Integer sort;

}
