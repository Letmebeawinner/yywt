package com.oa.entity.news;

import com.oa.annotation.Like;
import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 新闻发布
 *
 * @author lzh
 * @create 2017-11-01-17:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaNews extends BaseAuditEntity{
    @Like
    private String title;//新闻标题
    @Like
    private String content;//新闻内容

    private String summary;//新闻摘要
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
    //图片作者
    private String imageAuthor;
    //图片数量
    private Integer imageNumber;
    // 信息载体形式0 公文类， 1非公文类
    private Integer infoWay;
    //信息拟公开方式 公开方式 ， 1， 政府网站，2报刊，3广播，4电视，5政务公开栏，6校园网站，7其他
    private String publicWay;
    //信息拟公开时间
    private Date planPublicTime;
    //部门领导意见
    private String departmentOption;
    //信息管理员意见
    private String infoManagerOption;
    //责任主编意见
    private String infoLeaderOption;
    //附件地址
    private String fileUrl;
    //附件名
    private String fileName;
    // 0 允许公开， 1 不允许公开
    private Integer allowPublic;
    //不允许理由 0 国家机密， 1 商业机密，2个人隐私，3其他
    private String notAllowReason;
    //不公开凭据
    private String notAllowAccording;
    // 0 默认未发送  1 已发送到外网
    private Integer sendStatus;
    // 0默认未发送， 1 已发送到内网
    private Integer sendStatusInner;
    //内网id
    private Long innerId;
    //外网id
    private Long outerId;
}
