package com.oa.entity.letter;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公文
 *
 * @author ccl
 * @create 2017-03-08-18:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Letter extends BaseEntity {

    @Like
    private String title;//标题

    private Long sysUserId;//系统用户id

    private String context;//内容

    private Long typeId;//类型id
    //公文模板id
    private Integer templateTypeId;
}
