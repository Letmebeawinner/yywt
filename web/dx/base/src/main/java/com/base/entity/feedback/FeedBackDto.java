package com.base.entity.feedback;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 意见反馈
 *
 * @author ccl
 * @create 2017-03-14-14:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FeedBackDto extends FeedBack{
    /**
     * 系统用户名称
     */
    private String sysUserName;

}
