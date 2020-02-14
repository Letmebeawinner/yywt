package com.houqin.entity.lnventorylist;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2017/11/14 0014.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Lnventorylist extends BaseEntity {

    private String name;
    private String type;
    private String unit;
    private Long count;
    private String ysStatus;
    private Long meetingId;
}
