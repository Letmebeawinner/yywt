package com.houqin.entity.messArea;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 食堂区域
 *
 * @author YaoZhen
 * @create 06-19, 11:53, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessArea extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 区域描述
     */
    private String context;
    /**
     * 食堂id
     */
    private Long messId;
    /**
     * 位置
     */
    private String location;
}
