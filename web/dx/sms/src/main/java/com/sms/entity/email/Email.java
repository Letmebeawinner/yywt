package com.sms.entity.email;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义收件人，即根据管理员输入的邮箱地址发送邮件
 * 选择收件人，即根据用户的邮箱地址发送邮件
 *
 * @author sk
 * @since 2016-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Email extends BaseEntity {

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 发送类型 1 系统发送 2 管理员发送
     */
    private Integer sendType;

    /**
     * 发件人ID(管理员ID).如果是系统发送，ID为0
     */
    private Long userId;

    /**
     * 收件人(自定义发送：邮箱集合,以","分割,如1@qq.com, 2@qq.com；
     * 根据用户发送：用户ID集合，以","分割，如1, 2, 3)
     */
    private String receivers;

    /**
     * 发送备注(成功：发送成功; 失败：异常信息等)
     */
    private String remarks;

    private int custom;

}
