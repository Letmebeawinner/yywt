package com.oa.entity.paper;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封条用途
 *
 * @author lzh
 * @create 2017-01-05-10:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaperFunction extends BaseEntity {
    @Like
    private String name;
}
