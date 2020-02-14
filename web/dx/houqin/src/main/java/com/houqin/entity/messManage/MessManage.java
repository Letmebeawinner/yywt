package com.houqin.entity.messManage;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 食堂管理员
 *
 * @author ccl
 * @create 2016-12-23-10:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessManage extends BaseEntity{
    private Long messId;//食堂id

    private String manage;//食堂管理员

    private String duty;//食堂值班人员

    private Date attendtime;//值班时间

}
