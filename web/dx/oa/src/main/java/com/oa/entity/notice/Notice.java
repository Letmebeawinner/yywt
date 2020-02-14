package com.oa.entity.notice;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公告
 *
 * @author ccl
 * @create 2016-12-29-17:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notice extends BaseEntity {

    private Long sysUserId;//发布人
    private Long typeId;//类型id
    @Like
    private String title;//标题
    private String keyword;//关键字
    private String context;//内容
    private Integer publish;//发布0未发布，1已发布


}
