package com.keyanzizheng.entity.result;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课题-教职工
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskEmployee extends BaseEntity {

    private static final long serialVersionUID = 7797542166532375325L;
    private Long taskId;//课题id
    private Long employeeId;//教职工id

}
