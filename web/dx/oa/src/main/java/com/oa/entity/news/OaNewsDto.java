package com.oa.entity.news;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新闻发布
 *
 * @author lzh
 * @create 2017-11-01-17:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaNewsDto extends OaNews{
   private String userName;
   private int num;
}
