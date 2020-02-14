package com.oa.entity.paper;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封条类型
 *
 * @author lzh
 * @create 2017-01-04-17:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaperType extends BaseEntity{
    @Like
    private String name;
}
