package com.information.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoArticle implements Serializable {
    private Long ID;
    //分类ID
    private int ClassID;
    //相应表的记录ID
    private int ItemID;
    //模型表名
    private String TableName;
    //标题
    private String Title;
    //外部链接
    private String LinkUrl;
    //附件地址
    private String FileUrl;
    //
    private String Inputer;
    //录入者
    private String Editor;
    //点击量
    private int Hits;
    //日点击数
    private int DayHits;
    //周点击数
    private int WeekHits;
    //月点击数
    private int MonthHits;
    //添加时间
    private Date AddTime;
    //修改时间
    private Date UpdateTime;
    //生成时间
    private Date CreateTime;
    //状态
    private int Status;
    //上次点击时间
    private Date LastHitTime;
    //标题颜色
    private String TitleFontColor;
    //标题字形，加粗斜体等
    private String TitleFontType;
    //标题前缀如，图片，推荐，
    private String IncludePic;
    //保存路径
    private String FilePath;
    //生成文件名
    private String FileName;
    //生成文件扩展名
    private String FileType;
    //推荐级别
    private int EliteLevel;
    //是否跳转
    private int IsADLink;
    //跳转地址
    private String ADLink;
    //首页图片
    private String TopImage;
    //语言类型
    private int LangID;
    //副标题
    private String SubTitle;
    //新闻关键词
    private String KeyWord;
    //来源
    private String CopyFrom;
    //简介
    private String SmallContent;
    //是否热点
    private int IsHot;
    //是否推荐
    private int IsRecommend;
    //是否可评论
    private int IsKommentar;
    //文章内容
    private String Content;
    //是否替换关键词
    private int IsReplaceKeyWord;
    //是否自动分页
    private int IsAutoPage;
    //自动分页字符数
    private int AutoPageStrNum;

}
