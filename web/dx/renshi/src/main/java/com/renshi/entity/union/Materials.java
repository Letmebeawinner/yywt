package com.renshi.entity.union;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工会物资
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Materials extends BaseEntity {

    private static final long serialVersionUID = 3146365033883735657L;
    private String material; //物资名称
    private Long number; //入库数量

}
