package com.renshi.entity.educate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 培训教职工
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class EducateEmployee extends BaseEntity {

    private static final long serialVersionUID = -4629013230551003049L;
    private Long educateId;//培训ID
    private Long employeeId;//教职工ID

}
