package com.oa.entity.publish;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发布管理实体
 *
 * @author lzh
 * @create 2016-12-30-14:10
 */
@Data
@EqualsAndHashCode(callSuper =  false)
public class PublishManager extends BaseEntity {
    private Long articleId;//资讯id（公告或者新闻）
    private Integer type;//0新闻1公告
    private Integer publish;//0未发布，1已发布
}
