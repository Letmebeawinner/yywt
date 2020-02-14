package com.sms.entity.message;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2016/12/22.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsSendRecord extends BaseEntity {

    private static final long serialVersionUID = -1218397205833543414L;

    /**短信内容*/
    private String context;
    /**发送类型 1：系统发送 2：管理员发送 3：用户对发*/
    private Integer sendType;
    /**发送者id*/
    private Long userId;
    /*接收者手机号*/
    private String mobiles;
}
