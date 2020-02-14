package com.houqin.entity.managemobile;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by caichenglong on 2017/10/24.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ManageMobile extends BaseEntity {

    private String mobile;

}
