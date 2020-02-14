package com.houqin.entity.mess;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 食堂管理
 *
 * @author ccl
 * @create 2016-12-22-17:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Mess extends BaseEntity {

    @Like
    private String name;//食堂名称

    private Long peopleNum;//容纳人数

    private String morning;//早饭时间

    private String noon;//午饭时间

    private String night;//晚饭时间

    private String context;//备注


}
