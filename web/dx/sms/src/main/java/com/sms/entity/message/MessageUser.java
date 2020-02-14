package com.sms.entity.message;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2016/12/29.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageUser extends BaseEntity {
    private static final long serialVersionUID = -171875078292044883L;

    private Long recodeId;//短信记录id
    private Long receiveUserId;//接收短信的学员id
}
