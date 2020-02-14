package com.oa.entity.paper;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封条
 *
 * @author lzh
 * @create 2017-01-04-17:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Paper extends BaseEntity {
    @Like
    private String name;//封条名字

    private String fileUrl;//文件地址
    @Like
    private String description;//封条描述
    private Long paperTypeId;//封条类型id
    private Long paperFunctionId;//封条用途id
    private Long departmentId;//部门id
    @Like
    private String version;//版本号
}
