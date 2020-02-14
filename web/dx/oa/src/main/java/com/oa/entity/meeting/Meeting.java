package com.oa.entity.meeting;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会议室配置
 *
 * @author ccl
 * @create 2016-12-27-16:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Meeting extends BaseEntity {
    @Like
    private String name;//会议名称

    private Long amount;//容量

    private String place;//地点

    private String configure;//配置
}
