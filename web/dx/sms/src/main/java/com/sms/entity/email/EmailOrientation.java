package com.sms.entity.email;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定向发送，即根据用户选择接收人时的实体类
 *
 * @author sk
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EmailOrientation extends BaseEntity {

    /**
     * 收件人的ID(用户的id)
     */
    private Long receiverId;

    /**
     * 发送邮件记录的ID
     */
    private Long emailId;
}
