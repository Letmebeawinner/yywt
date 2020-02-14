package com.oa.entity.message;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoUserReceive extends BaseEntity {

    /**
     * 消息类型
     *
     */
    private Integer infoType;
    /**
     * 消息id
     */
    private Long infoId;
    /**
     * 接收人id
     */
    private Long receiverId;
    /**
     * 发送人id
     */
    private Long senderId;
    /**
     * 消息内容
     */
    private String content;
}
