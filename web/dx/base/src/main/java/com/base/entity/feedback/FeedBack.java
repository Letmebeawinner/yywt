package com.base.entity.feedback;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 意见反馈
 *
 * @author ccl
 * @create 2017-03-14-11:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FeedBack extends BaseEntity {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 留言内容
     */
    private String context;
    /**
     * 联系方式
     */
    private String connect;

}
