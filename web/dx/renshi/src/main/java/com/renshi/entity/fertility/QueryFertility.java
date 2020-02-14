package com.renshi.entity.fertility;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 计生管理
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryFertility extends Fertility {

    private static final long serialVersionUID = -3254945126872724676L;
    private String employeeName;  //教职工名称
    public QueryFertility(){}
    public QueryFertility(Fertility fertility){
        this.setId(fertility.getId());
        this.setEmployeeId(fertility.getEmployeeId());
        this.setUrl(fertility.getUrl());
        this.setName(fertility.getName());
        this.setContractUrl(fertility.getContractUrl());
        this.setCreateTime(fertility.getCreateTime());
        this.setIfPass(fertility.getIfPass());
    }
}
