package com.houqin.entity.lock;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class TempCard extends BaseEntity {

    //姓名
    private String userName;
    //性别
    private String sex;
    //房间id
    private Long roomId;
    //房间号码
    private String roomNo;
    //身份证号
    private String idNumber;
    //房间卡
    private String cardNo;
    //房间号
    private String cridentialId;
    //考勤卡
    private String timeCardNo;
    //开始时间
    private Date beginTime;
    //结束时间
    private Date endTime;

    private String context;

    //临时属性，不存数据库
    private String classId;
}
