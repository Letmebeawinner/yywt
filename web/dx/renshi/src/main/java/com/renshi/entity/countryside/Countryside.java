package com.renshi.entity.countryside;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 下乡帮扶
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Countryside extends BaseEntity {

    private String name;//姓名

    private String place;  //下乡地点

    private String content;  //下乡内容

    private Date  beginTime;  //开始时间

    private Date  endTime;  //预计结束时间

    private Long  number;  //预参与人数

    private Long  joinNumber;  //已参加人数


}
