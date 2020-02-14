package com.lock.entity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PassInfo extends BaseEntity {

    private Long UserId;//用户id

    private String CardId;//人员卡号

    private String UserName;//用户姓名

    private String PassTime;//刷卡时间

    private Long bedroomid;//房间id

    private String bedRoomName;


}
