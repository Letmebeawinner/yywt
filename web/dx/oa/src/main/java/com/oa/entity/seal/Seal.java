package com.oa.entity.seal;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 印章实体
 *
 * @author lzh
 * @create 2016-12-28-17:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Seal extends BaseEntity{
    @Like
    private String name;//印章名字
    @Like
    private String address;//存放地址
    @Like
    private String note;//笔记
    private Long sealTypeId;//印章类型id
    private Long sealFunctionId;//印章用途id
}
