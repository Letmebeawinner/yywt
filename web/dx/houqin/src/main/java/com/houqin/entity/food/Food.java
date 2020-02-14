package com.houqin.entity.food;

import com.a_268.base.core.BaseEntity;
import com.houqin.entity.mess.Mess;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 食材
 *
 * @author wanghailong
 * @create 2017-07-25-下午 1:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Food extends BaseEntity {
    private String name;//名称
    private Integer cont;//数量
    private String unit;//单位
    private String expirationDate;//保质期
    private Integer inven;//库存
    private Long messId;//食堂id
    private Mess mess;
}
