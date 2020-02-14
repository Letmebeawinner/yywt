package com.keyanzizheng.entity.contribute;

import com.keyanzizheng.entity.contribute.Contribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 投稿
 * Created by tiger on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryContribute extends Contribute {

    private static final long serialVersionUID = 7293154892054432084L;
    private String employeeName; //教职工姓名
    public QueryContribute(){}
    public QueryContribute(Contribute contribute){
        this.setId(contribute.getId());
        this.setEmployeeId(contribute.getEmployeeId());
        this.setName(contribute.getName());
        this.setFormUrl(contribute.getFormUrl());
        this.setIfPass(contribute.getIfPass());
        this.setCreateTime(contribute.getCreateTime());
    }

}
