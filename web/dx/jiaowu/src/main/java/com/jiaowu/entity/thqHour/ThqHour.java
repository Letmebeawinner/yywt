package com.jiaowu.entity.thqHour;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YaoZhen
 * @date 05-29, 13:53, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ThqHour extends BaseEntity {

    /**
     * hour
     */
    private Long hour;
    /**
     * thqId
     */
    private Long thqId;
}
