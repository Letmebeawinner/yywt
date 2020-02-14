package com.oa.entity.car;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Cut;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lizhenhui
 * @create 2016/12/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Car extends BaseEntity {
    @Like
    private String name;//车辆名
    @Like
    private String carID;//车牌号
    @Cut
    private Date repairDate;//保养日期
    private BigDecimal carRunDistance;//行车距离
    @Like
    private String sendPeople;//送保人
    private String description;//描述
    @Like
    private String repairCompany;//保养单位
    private Integer inUse;//0未出车，1已出车
}
