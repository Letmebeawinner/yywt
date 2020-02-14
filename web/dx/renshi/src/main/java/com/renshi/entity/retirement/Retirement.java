package com.renshi.entity.retirement;

import com.a_268.base.core.BaseEntity;
import com.renshi.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 离退休
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Retirement  extends BaseEntity {

    private Long employeeId; //教职工id
    @Like
    private String name;//姓名
    private Integer sex;//性别
    private Date birthday;//出生日期
    private String nationality;//民族
    private Date applyTime;  //申请退休的时间
    private Date workTime;//参加工作时间
    private String education;
    private Date enterpartyTime;//入党日期
    private String category;//类别
    private String  treatment;//待遇
    private String presentPost;//离职前职务

    private Integer outType;//类型  1，离退休，2转出

}
