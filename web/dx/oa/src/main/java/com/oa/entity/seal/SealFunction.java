package com.oa.entity.seal;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 印章用途实体
 * @author lzh
 * @create 2016-12-28-17:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SealFunction extends BaseEntity{
    @Like
    private String name;//用途名
}
