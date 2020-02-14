package com.sms.entity.info;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoUserReceive extends BaseEntity {

    /**
     * 消息类型
     * {@link com.sms.entity.info.InfoRecord.InfoType}
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
    /**
     * 附件
     */
    private String fileUrl;
    /**
     * 附件名
     */
    private String fileName;

}
