package com.oa.entity.countryside;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 下乡人员
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CountrysideEmp extends BaseEntity {

    private static final long serialVersionUID = 6778326897802778965L;
    private Long countrysideId;//下乡活动ID
    private Long employeeId;//教职工ID
    private Integer joinStatus;//参加状态 1:未参加 2已参加
    private String name;
    private Integer sex;
    private String nationality;
    private String mobile;


}
