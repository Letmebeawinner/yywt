package com.oa.entity.message;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoRecord extends BaseEntity {

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

    public class InfoType {
        /**
         * 系统发送
         */
        public static final int SEND_BY_SYS = 0;
        /**
         * 管理员向所有人发送
         */
        public static final int SEND_ALL = 1;
        /**
         * 管理员向部分人发送
         */
        public static final int SEND_SOME_PEOPLE = 2;
    }
}
