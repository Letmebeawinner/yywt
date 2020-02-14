package com.information.entity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoClass extends BaseEntity{

    //父id
    private Long parentID;

    //标题
    private String title;

}
