package com.keyanzizheng.entity.result;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResultFormStatistic extends BaseEntity {

    //类型名称
    private String name;

    //数量
    private int num;


}
