package com.oa.entity.rule;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 规章制度
 *
 * @author ccl
 * @create 2017-01-04-19:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Rule extends BaseEntity{

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
}
