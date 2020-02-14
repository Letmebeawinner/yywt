package com.houqin.entity.messtype;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 食堂类型管理
 *
 * @author ccl
 * @create 2016-12-22-16:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessType extends BaseEntity {

    private String name;//食堂类型名称

    private String location;//位置

    private String context;//内容

}
