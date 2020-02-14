package com.lock.entity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserBedroomRef extends BaseEntity {

    private int userBedroomId;

    private int userId;

    private int bedroomId;


}
