package com.lock.entity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by caichenglong on 2017/10/25.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BedRoom extends BaseEntity {

    private Long id;

    private String Name;

    private String code;

}
