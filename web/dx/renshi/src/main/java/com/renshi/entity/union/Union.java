package com.renshi.entity.union;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工会组织
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Union extends BaseEntity {

    private static final long serialVersionUID = -5715312478832372279L;
    private String name; //组织称呼
    private String contacts; //联系人
    private String mobile; //联系方式
    private String info; //简介
}
