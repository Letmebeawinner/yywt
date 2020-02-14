package com.sms.entity.info;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoUserRecord extends BaseEntity {

    /**
     * 消息id
     */
    private Long infoId;
    /**
     * 接收人id
     */
    private Long receiverId;
}
