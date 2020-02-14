package com.houqin.entity.equipment;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 器材管理
 * Created by Administrator on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Equipment extends BaseEntity{
    /**
     * 器材名称
     */
    @Like
    private String name;
    /**
     * 器材维护情况
     */
    private String context;
    /**
     * 消防器材位置
     */
    private String location;
    /**
     * 数量
     */
    private Long num;
    /**
     * 单位
     */
    private Long unitId;



    /**
     * 单位名称
     */
    private String unitName;

}
