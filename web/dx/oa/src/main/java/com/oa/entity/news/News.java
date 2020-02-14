package com.oa.entity.news;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新闻
 *
 * @author lzh
 * @create 2016-12-29-17:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class News extends BaseEntity{
    @Like
    private String title;//新闻标题
    @Like
    private String content;//新闻内容
    /*@Like
    private String keyword;//关键字*/

    private String subTitle;//副标题

    private String source;//来源

    private String author;//作者

    private String indexImage;//首页图片

    private String link;//外部链接

    private String brief;//简介

    private Integer canComment;//是否可评论

    private Integer hot;//热点,1代表是热点
    private Integer recommend;//推荐，1代表是推荐


    private Long newsTypeId;//新闻类型Id;
    private Long userId;//系统用户Id
    private Integer publish;//发布状态，0未发布，1已发布

    //status,已删除 0，通过 1，草稿 2，归档 3，待审核 4.

    private int num;

    private String userName;
}
