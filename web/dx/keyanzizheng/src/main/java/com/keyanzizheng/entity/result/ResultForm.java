package com.keyanzizheng.entity.result;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成果类型
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ResultForm extends BaseEntity {

    private static final long serialVersionUID = -6170404097720818777L;
    private String name;//类型名称
}
