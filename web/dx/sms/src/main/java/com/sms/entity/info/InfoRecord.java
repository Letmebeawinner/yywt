package com.sms.entity.info;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoRecord extends BaseEntity {

    private static final long serialVersionUID = 2644683974583334441L;
    
    /**
     * 消息类型
     * {@link InfoType}
     */
    private Integer infoType;
    /**
     * 发送人id
     */
    private Long senderId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送时间
     */
    private Timestamp sendTime;

    private Integer status;
    /**
     * 附件
     */
    private String fileUrl;
    /**
     * 附件名
     */
    private String fileName;

    public class InfoType {

        /**
         * 系统向部分人发送消息
         */
        public static final int SYS_SEND_SOME = 0;

        /**
         * 系统向所有人发送消息
         */
        public static final int SYS_SEND_ALL = 1;

        /**
         * 管理员向部分人发送消息
         */
        public static final int ADMIN_SEND_SOME = 2;

        /**
         * 管理员向所有人发送消息
         */
        public static final int ADMIN_SEND_ALL = 3;

        /**
         * 用户向用户发送消息
         */
        public static final int USER_SEND_USER = 4;
    }
}
