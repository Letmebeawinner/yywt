package com.keyanzizheng.entity.result;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课题变更记录
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskChange extends BaseEntity {

    private static final long serialVersionUID = -5922933724650156595L;
    private Long taskId; //课题id
    private String operate; //操作
}
