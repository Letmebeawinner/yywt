package com.oa.entity.rule;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.DATE;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 涌入查询的试题
 *
 * @author ccl
 * @create 2017-01-04-19:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRule extends BaseEntity{

    /**
     * 类型id
     */
    private Long typeId;

    /**
     * 标题
     */
    @Like
    private String name;

    /**
     * 来源
     */
    private String source;

    /**
     * 作者
     */
    private String author;

    /**
     * 浏览数量
     */
    private Integer views;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 内容
     */
    private String context;

    /**
     * 是否显示首页
     */
    private Long showIndex;

    /**
     * 创建起始时间 (用于查询, 默认为创建时间大于现在的时间,
     * 可以自定义比如说参数加上(value = "updateTime") ,即改为更新时间)
     */
    @DATE
    private Date startFromDate;
    /**
     * 创建结束时间 (用于查询， 创建时间小于这个字段的时间)
     */
    @DATE(start = false)
    private Date startToDate;

}
