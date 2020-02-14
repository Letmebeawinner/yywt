package com.oa.entity.news;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新闻拓展表
 *
 * @author lzh
 * @create 2016-12-28-17:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewsDto extends News {
    private String newsTypeName;//新闻类型名
    private String publishName;//发布人
}
