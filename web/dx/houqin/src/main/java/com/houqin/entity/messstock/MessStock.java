package com.houqin.entity.messstock;

import com.a_268.base.core.BaseEntity;
import com.houqin.entity.mess.Mess;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 食堂库存列表
 *
 * @author wanghailong
 * @create 2017-06-15-上午 9:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessStock extends BaseEntity {

    private String name;//名称
    private String units="";//单位
    private Mess mess; //楼层，用于查询使用。
    private String content;//内容
    private String foodTypeContent;//进货名称
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expirationTime;//过期时间

    /**
     * 在表中已不存在
     * 仅用作展示
     */
    private Integer count;//数量
    private Double price;//价格
}
